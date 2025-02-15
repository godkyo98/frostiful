package com.github.thedeathlycow.frostiful.survival;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.config.group.FreezingConfigGroup;
import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import com.github.thedeathlycow.thermoo.api.ThermooAttributes;
import com.github.thedeathlycow.thermoo.api.temperature.EnvironmentController;
import com.github.thedeathlycow.thermoo.api.temperature.EnvironmentControllerDecorator;
import com.github.thedeathlycow.thermoo.api.temperature.Soakable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.MathHelper;

public class EntityTemperatureController extends EnvironmentControllerDecorator {

    private static final double BASE_MIN_TEMPERATURE = 40;

    public EntityTemperatureController(EnvironmentController controller) {
        super(controller);
    }

    @Override
    public double getBaseValueForAttribute(RegistryEntry<EntityAttribute> attribute, LivingEntity entity) {
        double base = controller.getBaseValueForAttribute(attribute, entity);

        if (base != 0) {
            return base;
        }

        if (attribute == ThermooAttributes.MIN_TEMPERATURE) {
            base = BASE_MIN_TEMPERATURE;
        }

        return base;
    }
}
