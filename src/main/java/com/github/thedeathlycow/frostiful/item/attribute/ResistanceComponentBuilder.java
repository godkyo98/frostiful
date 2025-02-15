package com.github.thedeathlycow.frostiful.item.attribute;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.registry.FItems;
import com.github.thedeathlycow.thermoo.api.ThermooAttributes;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.Set;

public final class ResistanceComponentBuilder {
    public static void initialize() {
        DefaultItemComponentEvents.MODIFY.register(context -> {
            Set<Item> netheriteItems = Set.of(
                    Items.NETHERITE_HELMET,
                    Items.NETHERITE_CHESTPLATE,
                    Items.NETHERITE_LEGGINGS,
                    Items.NETHERITE_BOOTS
            );

            Set<Item> furItems = Set.of(
                    FItems.FUR_HELMET,
                    FItems.FUR_CHESTPLATE,
                    FItems.FUR_LEGGINGS,
                    FItems.FUR_BOOTS,
                    FItems.FUR_PADDED_CHAINMAIL_HELMET,
                    FItems.FUR_PADDED_CHAINMAIL_CHESTPLATE,
                    FItems.FUR_PADDED_CHAINMAIL_LEGGINGS,
                    FItems.FUR_PADDED_CHAINMAIL_BOOTS
            );

            modify(context, netheriteItems, Frostiful.getConfig().combatConfig.getProtectiveFrostResistanceMultiplier());
            modify(context, furItems, Frostiful.getConfig().combatConfig.getVeryProtectiveFrostResistanceMultiplier());
        });
    }

    private static void modify(DefaultItemComponentEvents.ModifyContext context, Set<Item> items, double multiplier) {
        context.modify(
                items,
                (builder, item) -> {
                    if (item instanceof ArmorItem armor) {
                        AttributeModifiersComponent component = getOrDefault(builder, item);
                        component = build(armor, component, multiplier);
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