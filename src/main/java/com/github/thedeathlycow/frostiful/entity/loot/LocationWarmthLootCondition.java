package com.github.thedeathlycow.frostiful.entity.loot;

import com.github.thedeathlycow.frostiful.registry.FLootConditionTypes;
import com.github.thedeathlycow.frostiful.survival.PassiveTemperatureEffects;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.predicate.NumberRange;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Objects;

public record LocationWarmthLootCondition(
        NumberRange.IntRange value
) implements LootCondition {

    public static final MapCodec<LocationWarmthLootCondition> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    NumberRange.IntRange.CODEC
                            .fieldOf("value")
                            .orElse(NumberRange.IntRange.ANY)
                            .forGetter(LocationWarmthLootCondition::value)
            ).apply(instance, LocationWarmthLootCondition::new)
    );

    @Override
    public LootConditionType getType() {
        return FLootConditionTypes.LOCATION_WARMTH;
    }

    @Override
    public boolean test(LootContext lootContext) {
        World world = lootContext.getWorld();
        BlockPos pos = BlockPos.ofFloored(Objects.requireNonNull(lootContext.get(LootContextParameters.ORIGIN)));

        int areaWarmth = PassiveTemperatureEffects.getBlockLightTemperatureChange(world, pos);
        return this.value.test(areaWarmth);
    }

    public static LootCondition.Builder builder(NumberRange.IntRange value) {
        return () -> new LocationWarmthLootCondition(value);
    }
}