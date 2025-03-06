package com.github.thedeathlycow.frostiful.item.component;

import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.registry.tag.FItemTags;
import com.github.thedeathlycow.thermoo.api.armor.material.ArmorMaterialTags;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.StringIdentifiable;

import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;

public enum FrostResistanceLevelComponent implements StringIdentifiable {
    VERY_PROTECTIVE(
            "very_protective",
            ArmorMaterialTags.VERY_RESISTANT_TO_COLD,
            FItemTags.IS_VERY_PROTECTIVE_FROST_RESISTANCE,
            c -> c.combatConfig.getVeryProtectiveFrostResistanceMultiplier()
    ),
    PROTECTIVE(
            "protective",
            ArmorMaterialTags.RESISTANT_TO_COLD,
            FItemTags.IS_PROTECTIVE_FROST_RESISTANCE,
            c -> c.combatConfig.getProtectiveFrostResistanceMultiplier()
    ),
    NEUTRAL("neutral", c -> false, c -> 0),
    HARMFUL(
            "harmful",
            ArmorMaterialTags.WEAK_TO_COLD,
            FItemTags.IS_HARMFUL_FROST_RESISTANCE,
            c -> -c.combatConfig.getProtectiveFrostResistanceMultiplier()),
    VERY_HARMFUL(
            "very_harmful",
            ArmorMaterialTags.VERY_WEAK_TO_COLD,
            FItemTags.IS_VERY_HARMFUL_FROST_RESISTANCE,
            c -> -c.combatConfig.getVeryProtectiveFrostResistanceMultiplier()
    );

    public static final Codec<FrostResistanceLevelComponent> CODEC = StringIdentifiable.createCodec(
            FrostResistanceLevelComponent::values
    );
    public static final PacketCodec<ByteBuf, FrostResistanceLevelComponent> PACKET_CODEC = PacketCodecs.indexed(
            i -> values()[i],
            Enum::ordinal
    );

    private final String name;
    private final Predicate<ItemStack> stackPredicate;
    private final ToDoubleFunction<FrostifulConfig> getMultiplier;

    FrostResistanceLevelComponent(
            String name,
            TagKey<ArmorMaterial> armorMaterialTag,
            TagKey<Item> itemTag,
            ToDoubleFunction<FrostifulConfig> getMultiplier
    ) {
        this(name, createTagPredicate(armorMaterialTag, itemTag), getMultiplier);
    }

    FrostResistanceLevelComponent(
            String name,
            Predicate<ItemStack> stackPredicate,
            ToDoubleFunction<FrostifulConfig> getMultiplier
    ) {
        this.name = name;
        this.stackPredicate = stackPredicate;
        this.getMultiplier = getMultiplier;
    }

    public static FrostResistanceLevelComponent forStack(ItemStack stack) {
        for (FrostResistanceLevelComponent level : values()) {
            if (level.stackPredicate.test(stack)) {
                return level;
            }
        }
        return NEUTRAL;
    }

    public double getMultiplier(FrostifulConfig config) {
        return this.getMultiplier.applyAsDouble(config);
    }

    private static Predicate<ItemStack> createTagPredicate(TagKey<ArmorMaterial> armorMaterialTag, TagKey<Item> itemTag) {
        return stack -> stack.isIn(itemTag)
                || (stack.getItem() instanceof ArmorItem armorItem && armorItem.getMaterial().isIn(armorMaterialTag));
    }

    @Override
    public String asString() {
        return this.name;
    }
}