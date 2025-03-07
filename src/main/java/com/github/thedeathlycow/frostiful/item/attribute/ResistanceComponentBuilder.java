package com.github.thedeathlycow.frostiful.item.attribute;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.registry.FArmorMaterials;
import com.github.thedeathlycow.frostiful.registry.FDataComponentTypes;
import com.github.thedeathlycow.thermoo.api.ThermooAttributes;
import com.github.thedeathlycow.thermoo.api.armor.material.ArmorMaterialTags;
import com.github.thedeathlycow.thermoo.api.item.ModifyItemAttributeModifiersCallback;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.*;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.ToDoubleFunction;

public final class ResistanceComponentBuilder {
    private static final Map<EquipmentSlot, Identifier> SLOT_IDS = new EnumMap<>(EquipmentSlot.class);
    private static final Map<EquipmentSlot, Identifier> ENVIRONMENT_SLOT_IDS = new EnumMap<>(EquipmentSlot.class);

    public static void initialize() {
        initializeComponentModifiers();
        initializeItemModifiers();
    }

    public static void applyLegacyArmorMaterialTags(ItemStack stack) {
        if (!stack.contains(FDataComponentTypes.FROST_RESISTANCE) && stack.getItem() instanceof ArmorItem armor) {
            RegistryEntry<ArmorMaterial> material = armor.getMaterial();
            if (material.isIn(ArmorMaterialTags.VERY_RESISTANT_TO_COLD)) {
                stack.set(FDataComponentTypes.FROST_RESISTANCE, FrostResistanceComponent.VERY_PROTECTIVE);
            } else if (material.isIn(ArmorMaterialTags.RESISTANT_TO_COLD)) {
                stack.set(FDataComponentTypes.FROST_RESISTANCE, FrostResistanceComponent.PROTECTIVE);
            } else if (material.isIn(ArmorMaterialTags.VERY_WEAK_TO_COLD)) {
                stack.set(FDataComponentTypes.FROST_RESISTANCE, FrostResistanceComponent.VERY_HARMFUL);
            } else if (material.isIn(ArmorMaterialTags.WEAK_TO_COLD)) {
                stack.set(FDataComponentTypes.FROST_RESISTANCE, FrostResistanceComponent.HARMFUL);
            }
        }
    }

    private static void initializeItemModifiers() {
        ModifyItemAttributeModifiersCallback.EVENT.register((stack, builder) -> {
            if (stack.getItem() instanceof ArmorItem armor) {
                FrostResistanceComponent resistance = stack.getOrDefault(
                        FDataComponentTypes.FROST_RESISTANCE,
                        FrostResistanceComponent.DEFAULT
                );

                EquipmentSlot slot = armor.getSlotType();
                AttributeModifierSlot attributeModifierSlot = AttributeModifierSlot.forEquipmentSlot(slot);
                FArmorType fArmorType = FArmorType.forArmorType(armor.getType());

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

    private static AttributeModifiersComponent getOrDefault(ComponentMap.Builder builder, Item item) {
        AttributeModifiersComponent component = builder.getOrDefault(
                DataComponentTypes.ATTRIBUTE_MODIFIERS,
                AttributeModifiersComponent.DEFAULT
        );

        if (component.modifiers().isEmpty()) {
            component = item.getAttributeModifiers();
        }

        return component;
    }

    private ResistanceComponentBuilder() {

    }
}