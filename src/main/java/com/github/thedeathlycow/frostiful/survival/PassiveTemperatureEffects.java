package com.github.thedeathlycow.frostiful.survival;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.block.SunLichenBlock;
import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.entity.component.SnowAccumulationComponent;
import com.github.thedeathlycow.frostiful.registry.tag.FBlockTags;
import com.github.thedeathlycow.frostiful.registry.tag.FEnchantmentTags;
import com.github.thedeathlycow.thermoo.api.temperature.event.EnvironmentTickContext;
import com.github.thedeathlycow.thermoo.api.temperature.event.LivingEntityTemperatureTickEvents;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public final class PassiveTemperatureEffects {
    public static void initialize() {
        LivingEntityTemperatureTickEvents.GET_PASSIVE_TEMPERATURE_CHANGE.register(PassiveTemperatureEffects::getPassiveChange);
    }

    private static int getPassiveChange(EnvironmentTickContext<LivingEntity> context) {
        LivingEntity entity = context.affected();

        // don't touch scorchful's effects
        if (entity.isSpectator() || entity.thermoo$getTemperature() > 0) {
            return 0;
        }

        int total = 0;

        FrostifulConfig config = Frostiful.getConfig();
        total += getHotFloorTemperatureChange(context, config);
        total += getAndUpdateBlockLightTemperatureChange(context);

        return total;
    }

    private static int getHotFloorTemperatureChange(EnvironmentTickContext<LivingEntity> context, FrostifulConfig config) {
        LivingEntity entity = context.affected();
        BlockState steppingState = entity.getSteppingBlockState();

        if (steppingState.isIn(FBlockTags.HOT_FLOOR)) {
            ItemStack footStack = entity.getEquippedStack(EquipmentSlot.FEET);

            if (!EnchantmentHelper.hasAnyEnchantmentsIn(footStack, FEnchantmentTags.IS_FROSTY)) {
                // TODO: fix fire particles
                SunLichenBlock.createFireParticles(context.world(), entity.getBlockPos());
                return config.freezingConfig.getHeatFromHotFloor();
            }
        }

        return 0;
    }

    private static int getAndUpdateBlockLightTemperatureChange(EnvironmentTickContext<LivingEntity> context) {
        int warmthFromLight = getBlockLightTemperatureChange(context.world(), context.pos());
        if (warmthFromLight > 0) {
            SnowAccumulationComponent.get(context.affected()).meltSnowAccumulation();
        }

        return warmthFromLight;
    }

    /**
     * Gets the temperature change from block light in the surrounding area. Exposed as public for the location_warmth
     * loot condition.
     */
    public static int getBlockLightTemperatureChange(World world, BlockPos pos) {
        FrostifulConfig config = Frostiful.getConfig();

        int lightLevel = world.getLightLevel(LightType.BLOCK, pos);
        int minLightLevel = config.environmentConfig.getMinLightForWarmth();

        int warmth = 0;
        if (lightLevel >= minLightLevel) {
            warmth = config.environmentConfig.getWarmthPerLightLevel() * (lightLevel - minLightLevel);
        }

        return warmth;
    }

    private PassiveTemperatureEffects() {

    }
}