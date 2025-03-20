package com.github.thedeathlycow.frostiful.item.attribute;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.registry.FDataComponentTypes;
import com.github.thedeathlycow.thermoo.api.ThermooAttributes;
import com.github.thedeathlycow.thermoo.api.item.ModifyItemAttributeModifiersCallback;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class ResistanceComponentBuilder {
    private static final Map<EquipmentSlot, Identifier> SLOT_IDS = new EnumMap<>(EquipmentSlot.class);
    private static final Map<EquipmentSlot, Identifier> ENVIRONMENT_SLOT_IDS = new EnumMap<>(EquipmentSlot.class);

    public static void initialize() {
        initializeComponentModifiers();
        initializeItemModifiers();
    }

    private static void initializeItemModifiers() {
        ModifyItemAttributeModifiersCallback.EVENT.register((stack, builder) -> {
            if (stack.isIn(ConventionalItemTags.ARMORS) && stack.contains(DataComponentTypes.EQUIPPABLE)) {
                FrostResistanceComponent resistance = stack.getOrDefault(
                        FDataComponentTypes.FROST_RESISTANCE,
                        FrostResistanceComponent.DEFAULT
                );

                EquippableComponent equippable = stack.get(DataComponentTypes.EQUIPPABLE);
                EquipmentSlot slot = equippable.slot();
                AttributeModifierSlot attributeModifierSlot = AttributeModifierSlot.forEquipmentSlot(slot);
                FArmorType fArmorType = FArmorType.forEquipmentSlot(slot);

                if (resistance.frostResistanceMultiplier() != 0) {
                    builder.add(
                            ThermooAttributes.FROST_RESISTANCE,
                            new EntityAttributeModifier(
                                    SLOT_IDS.computeIfAbsent(
                                            slot,
                                            sl -> Frostiful.id("base_frost_resistance/" + sl.asString())
                                    ),
                                    fArmorType.getBaseFrostResistance() * resistance.frostResistanceMultiplier(),
                                    EntityAttributeModifier.Operation.ADD_VALUE
                            ),
                            attributeModifierSlot
                    );
                }

                if (resistance.environmentFrostResistanceMultiplier() != 0) {
                    builder.add(
                            ThermooAttributes.ENVIRONMENT_FROST_RESISTANCE,
                            new EntityAttributeModifier(
                                    ENVIRONMENT_SLOT_IDS.computeIfAbsent(
                                            slot,
                                            sl -> Frostiful.id("base_environment_frost_resistance/" + sl.asString())
                                    ),
                                    fArmorType.getBaseEnvironmentFrostResistance() * resistance.environmentFrostResistanceMultiplier(),
                                    EntityAttributeModifier.Operation.ADD_VALUE
                            ),
                            attributeModifierSlot
                    );
                }
            }
        });
    }

    private static void initializeComponentModifiers() {
        DefaultItemComponentEvents.MODIFY.register(context -> {
            context.modify(
                    List.of(
                            Items.NETHERITE_HELMET,
                            Items.NETHERITE_CHESTPLATE,
                            Items.NETHERITE_LEGGINGS,
                            Items.NETHERITE_BOOTS
                    ),
                    (builder, item) -> {
                        builder.add(FDataComponentTypes.FROST_RESISTANCE, FrostResistanceComponent.PROTECTIVE);
                    }
            );

            context.modify(
                    Items.TURTLE_HELMET,
                    builder -> {
                        builder.add(FDataComponentTypes.FROST_RESISTANCE, FrostResistanceComponent.VERY_HARMFUL);
                    }
            );
        });
    }

    private ResistanceComponentBuilder() {

    }
}