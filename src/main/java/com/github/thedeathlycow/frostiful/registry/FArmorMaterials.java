package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.registry.tag.FItemTags;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;

import java.util.EnumMap;

public class FArmorMaterials {
    public static final RegistryKey<EquipmentAsset> FUR_ASSET = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Frostiful.id("fur"));
    public static final RegistryKey<EquipmentAsset> FUR_LINED_CHAINMAIL_ASSET = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Frostiful.id("fur_lined_chainmail"));

    public static final ArmorMaterial FUR = new ArmorMaterial(
            5,
            Util.make(new EnumMap<>(EquipmentType.class), map -> {
                map.put(EquipmentType.BOOTS, 1);
                map.put(EquipmentType.LEGGINGS, 2);
                map.put(EquipmentType.CHESTPLATE, 3);
                map.put(EquipmentType.HELMET, 1);
                map.put(EquipmentType.BODY, 3);
            }),
            15,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC,
            0.0f, 0.0f,
            FItemTags.REPAIRS_FUR_ARMOR,
            FUR_ASSET
    );

    public static final ArmorMaterial FUR_LINED_CHAINMAIL = new ArmorMaterial(
            5,
            Util.make(new EnumMap<>(EquipmentType.class), map -> {
                map.put(EquipmentType.BOOTS, 2);
                map.put(EquipmentType.LEGGINGS, 5);
                map.put(EquipmentType.CHESTPLATE, 6);
                map.put(EquipmentType.HELMET, 2);
                map.put(EquipmentType.BODY, 5);
            }),
            12,
            SoundEvents.ITEM_ARMOR_EQUIP_CHAIN,
            0.0f,
            0.0f,
            FItemTags.REPAIRS_FUR_LINED_CHAINMAIL_ARMOR,
            FUR_LINED_CHAINMAIL_ASSET
    );

    private FArmorMaterials() {

    }
}
