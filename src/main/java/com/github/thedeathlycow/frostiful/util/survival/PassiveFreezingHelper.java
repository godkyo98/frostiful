package com.github.thedeathlycow.frostiful.util.survival;

import com.github.thedeathlycow.frostiful.config.ConfigKeys;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.frostiful.tag.biome.FrostifulBiomeTemperatureTags;
import com.github.thedeathlycow.simple.config.Config;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class PassiveFreezingHelper {

    public static int getPassiveFreezing(LivingEntity livingEntity) {
        int biomeFreezing = getBiomeFreezing(livingEntity);
        if (biomeFreezing > 0) {
            biomeFreezing *= getPassiveFreezingMultiplier(livingEntity);
        }
        return biomeFreezing;
    }

    public static int getWarmth(LivingEntity livingEntity) {
        World world = livingEntity.getWorld();
        BlockPos pos = livingEntity.getBlockPos();
        Config config = Frostiful.getConfig();
        int warmth = 0;

        int lightLevel = world.getLightLevel(LightType.BLOCK, pos);
        if (lightLevel >= config.get(ConfigKeys.MIN_WARMTH_LIGHT_LEVEL)) {
            warmth += config.get(ConfigKeys.WARMTH_PER_LIGHT_LEVEL) * (lightLevel - config.get(ConfigKeys.MIN_WARMTH_LIGHT_LEVEL));
        }

        if (livingEntity.isOnFire()) {
            warmth += config.get(ConfigKeys.ON_FIRE_THAW_RATE);
        }

        return warmth;
    }

    public static int getPowderSnowFreezing(LivingEntity livingEntity) {
        Config config = Frostiful.getConfig();
        return livingEntity.inPowderSnow ?
                config.get(ConfigKeys.POWDER_SNOW_INCREASE_PER_TICK) :
                0;
    }

    public static double getPassiveFreezingMultiplier(LivingEntity livingEntity) {
        Config config = Frostiful.getConfig();
        double multiplier = 0.0D;

        if (livingEntity.isWet()) {
            multiplier += config.get(ConfigKeys.WET_FREEZE_RATE_MULTIPLIER);
        }

        return multiplier == 0.0D ? 1 : multiplier;
    }

    public static int getBiomeFreezing(LivingEntity livingEntity) {
        World world = livingEntity.getWorld();
        BlockPos pos = livingEntity.getBlockPos();
        Config config = Frostiful.getConfig();

        if (!livingEntity.canFreeze()) {
            return -config.get(ConfigKeys.WARM_BIOME_THAW_RATE);
        }

        RegistryEntry<Biome> biomeIn = world.getBiome(pos);

        boolean freezingBelowDamageThreshold = livingEntity.getFrozenTicks() < livingEntity.getMinFreezeDamageTicks();
        if (biomeIn.isIn(FrostifulBiomeTemperatureTags.IS_CHILLY) && freezingBelowDamageThreshold) {
            return config.get(ConfigKeys.CHILLY_BIOME_FREEZE_RATE);
        } else if (biomeIn.isIn(FrostifulBiomeTemperatureTags.IS_COLD) && freezingBelowDamageThreshold) {
            return config.get(ConfigKeys.COLD_BIOME_FREEZE_RATE);
        } else if (biomeIn.isIn(FrostifulBiomeTemperatureTags.IS_FREEZING) && freezingBelowDamageThreshold) {
            return config.get(ConfigKeys.FREEZING_BIOME_FREEZE_RATE);
        } else if (!freezingBelowDamageThreshold) {
            return 0;
        } else {
            return -config.get(ConfigKeys.WARM_BIOME_THAW_RATE);
        }
    }

}