package com.github.thedeathlycow.frostiful.survival;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.item.cloak.AbstractFrostologyCloakItem;
import com.github.thedeathlycow.frostiful.registry.FGameRules;
import com.github.thedeathlycow.frostiful.registry.FItems;
import com.github.thedeathlycow.thermoo.api.environment.component.EnvironmentComponentTypes;
import com.github.thedeathlycow.thermoo.api.environment.component.TemperatureRecordComponent;
import com.github.thedeathlycow.thermoo.api.environment.event.EnvironmentTickContext;
import com.github.thedeathlycow.thermoo.api.environment.event.ServerPlayerEnvironmentTickEvents;
import com.github.thedeathlycow.thermoo.api.util.TemperatureUnit;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.MathHelper;

public final class ServerPlayerEnvironmentTickListeners {
    public static final double WETNESS_MULTIPLIER = 2.0;

    public static void initialize() {
        ServerPlayerEnvironmentTickEvents.GET_TEMPERATURE_CHANGE.register(ServerPlayerEnvironmentTickListeners::getTemperatureChange);
        ServerPlayerEnvironmentTickEvents.ALLOW_TEMPERATURE_CHANGE.register(ServerPlayerEnvironmentTickListeners::allowTemperatureChange);
    }

    private static int getTemperatureChange(EnvironmentTickContext<ServerPlayerEntity> context) {
        if (context.affected().isSpectator()) {
            return 0;
        }

        double temperatureC = context.components()
                .getOrDefault(EnvironmentComponentTypes.TEMPERATURE, TemperatureRecordComponent.DEFAULT)
                .temperature()
                .valueInUnit(TemperatureUnit.CELSIUS);

        if (temperatureC > 10.0) {
            return 0;
        }
        int total = MathHelper.ceil((temperatureC / 10.0) - 2);

        if (total < 0 && context.affected().thermoo$isWet()) {
            total = (int) (total * WETNESS_MULTIPLIER);
        }

        if (context.affected().age % 20 == 0 && Frostiful.LOGGER.isDebugEnabled()) {
            Frostiful.LOGGER.debug("Adding {} temperature to {}", total, context.affected().getNameForScoreboard());
        }

        return total;
    }

    private static TriState allowTemperatureChange(EnvironmentTickContext<ServerPlayerEntity> context, int temperatureChange) {
        if (temperatureChange > 0) {
            return TriState.DEFAULT;
        }

        FrostifulConfig config = Frostiful.getConfig();
        ServerPlayerEntity player = context.affected();

        int tickInterval = config.freezingConfig.getPassiveFreezingTickInterval();
        if (tickInterval > 1 && player.age % tickInterval != 0) {
            return TriState.FALSE;
        }

        if (player.thermoo$getTemperatureScale() < -config.freezingConfig.getMaxPassiveFreezingPercent()) {
            return TriState.FALSE;
        }

        boolean doPassiveFreezing = config.freezingConfig.doPassiveFreezing()
                && player.getWorld().getGameRules().getBoolean(FGameRules.DO_PASSIVE_FREEZING);

        if (doPassiveFreezing) {
            return TriState.TRUE;
        } else {
            return TriState.of(
                    AbstractFrostologyCloakItem.isWearing(
                            player,
                            stack -> stack.isOf(FItems.FROSTOLOGY_CLOAK)
                    )
            );
        }
    }

    private ServerPlayerEnvironmentTickListeners() {

    }
}