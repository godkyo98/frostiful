package com.github.thedeathlycow.frostiful.item.cloak;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.compat.FrostifulIntegrations;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.InventoryOwner;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.function.Predicate;

public abstract class AbstractFrostologyCloakItem extends Item {
    public static final Identifier MODEL_TEXTURE_ID = Frostiful.id("textures/entity/frostology_cloak.png");

    public AbstractFrostologyCloakItem(Settings settings) {
        super(settings.component(
                DataComponentTypes.EQUIPPABLE,
                EquippableComponent.builder(EquipmentSlot.CHEST)
                        .damageOnHurt(false)
                        .build()
        ));
    }

    public static boolean isWearing(LivingEntity entity, Predicate<ItemStack> isCloak) {
        if (FrostifulIntegrations.isModLoaded(FrostifulIntegrations.TRINKETS_ID)) {
            boolean trinket = TrinketsApi.getTrinketComponent(entity)
                    .map(trinketComponent -> trinketComponent.isEquipped(isCloak))
                    .orElse(false);

            if (trinket) {
                return true;
            }
        }

        return isCloak.test(entity.getEquippedStack(EquipmentSlot.CHEST));
    }
}
