package com.github.thedeathlycow.frostiful.item.attribute;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.item.component.FrostResistanceLevelComponent;
import com.github.thedeathlycow.frostiful.registry.FArmorMaterials;
import com.github.thedeathlycow.frostiful.registry.FDataComponentTypes;
import com.github.thedeathlycow.thermoo.api.ThermooAttributes;
import com.github.thedeathlycow.thermoo.api.item.ModifyItemAttributeModifiersCallback;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;

import java.util.function.ToDoubleFunction;

public final class ResistanceComponentBuilder {
    public static void initialize() {
        ModifyItemAttributeModifiersCallback.EVENT.register((stack, builder) -> {
            if (stack.isIn(ConventionalItemTags.ARMORS) && stack.contains(FDataComponentTypes.FROST_RESISTANCE_LEVEL)) {
                FrostResistanceLevelComponent level = stack.getOrDefault(FDataComponentTypes.FROST_RESISTANCE_LEVEL, FrostResistanceLevelComponent.NEUTRAL);


            }
        });


        DefaultItemComponentEvents.MODIFY.register(context -> {
            modify(context, armor -> {
                RegistryKey<ArmorMaterial> itemMaterial = armor.getMaterial().getKey().orElse(null);

                return itemMaterial != null && itemMaterial == ArmorMaterials.NETHERITE.getKey().orElse(null)
                        ? Frostiful.getConfig().combatConfig.getProtectiveFrostResistanceMultiplier()
                        : 0;
            });
            modify(context, armor -> {
                RegistryKey<ArmorMaterial> itemMaterial = armor.getMaterial().getKey().orElse(null);

                boolean isFurMaterial = itemMaterial == FArmorMaterials.FUR.getKey().orElse(null)
                        || itemMaterial == FArmorMaterials.FUR_LINED_CHAINMAIL.getKey().orElse(null);

                return itemMaterial != null && isFurMaterial
                        ? Frostiful.getConfig().combatConfig.getVeryProtectiveFrostResistanceMultiplier()
                        : 0;
            });
        });
    }

    private static void modify(DefaultItemComponentEvents.ModifyContext context, ToDoubleFunction<ArmorItem> multiplier) {
        context.modify(
                ArmorItem.class::isInstance,
                (builder, item) -> {
                    if (item instanceof ArmorItem armor) {
                        AttributeModifiersComponent component = getOrDefault(builder, item);
                        component = build(armor, component, multiplier.applyAsDouble(armor));
                        builder.add(DataComponentTypes.ATTRIBUTE_MODIFIERS, component);
                    }
                }
        );
    }

    private static AttributeModifiersComponent build(
            ArmorItem item,
            AttributeModifiersComponent component,
            double multiplier
    ) {
        if (multiplier == 0) {
            return component;
        }

        AttributeModifierSlot slot = AttributeModifierSlot.forEquipmentSlot(item.getSlotType());
        FArmorType type = FArmorType.forArmorType(item.getType());

        double frostResistance = type.getBaseFrostResistance() * multiplier;
        double environmentFrostResistance = type.getBaseEnvironmentFrostResistance() * multiplier;

        if (frostResistance != 0) {
            component = component.with(
                    ThermooAttributes.FROST_RESISTANCE,
                    new EntityAttributeModifier(
                            Frostiful.id("frost_resistance/" + type.asString()),
                            frostResistance,
                            EntityAttributeModifier.Operation.ADD_VALUE
                    ),
                    slot
            );
        }

        if (environmentFrostResistance != 0) {
            component = component.with(
                    ThermooAttributes.ENVIRONMENT_FROST_RESISTANCE,
                    new EntityAttributeModifier(
                            Frostiful.id("environment_frost_resistance/" + type.asString()),
                            environmentFrostResistance,
                            EntityAttributeModifier.Operation.ADD_VALUE
                    ),
                    slot
            );
        }

        return component;
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