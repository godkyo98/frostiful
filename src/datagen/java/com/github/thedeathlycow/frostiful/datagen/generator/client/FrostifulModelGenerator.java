package com.github.thedeathlycow.frostiful.datagen.generator.client;

import com.github.thedeathlycow.frostiful.client.render.FrostWandItemRenderer;
import com.github.thedeathlycow.frostiful.registry.FArmorMaterials;
import com.github.thedeathlycow.frostiful.registry.FItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.*;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.render.item.model.special.TridentModelRenderer;
import net.minecraft.item.Item;

public class FrostifulModelGenerator extends FabricModelProvider {
    private static final String HELMET = "helmet";
    private static final String CHESTPLATE = "chestplate";
    private static final String LEGGINGS = "leggings";
    private static final String BOOTS = "boots";

    public FrostifulModelGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        // not generating block states atm
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        this.registerFrostWand(FItems.FROST_WAND, itemModelGenerator);
        itemModelGenerator.registerArmor(FItems.FUR_HELMET, FArmorMaterials.FUR_ASSET, HELMET, false);
        itemModelGenerator.registerArmor(FItems.FUR_CHESTPLATE, FArmorMaterials.FUR_ASSET, CHESTPLATE, false);
        itemModelGenerator.registerArmor(FItems.FUR_LEGGINGS, FArmorMaterials.FUR_ASSET, LEGGINGS, false);
        itemModelGenerator.registerArmor(FItems.FUR_BOOTS, FArmorMaterials.FUR_ASSET, BOOTS, false);
        itemModelGenerator.registerArmor(FItems.ICE_SKATES, FArmorMaterials.FUR_ASSET, BOOTS, false);

        itemModelGenerator.registerArmor(FItems.FUR_PADDED_CHAINMAIL_HELMET, FArmorMaterials.FUR_LINED_CHAINMAIL_ASSET, HELMET, false);
        itemModelGenerator.registerArmor(FItems.FUR_PADDED_CHAINMAIL_CHESTPLATE, FArmorMaterials.FUR_LINED_CHAINMAIL_ASSET, CHESTPLATE, false);
        itemModelGenerator.registerArmor(FItems.FUR_PADDED_CHAINMAIL_LEGGINGS, FArmorMaterials.FUR_LINED_CHAINMAIL_ASSET, LEGGINGS, false);
        itemModelGenerator.registerArmor(FItems.FUR_PADDED_CHAINMAIL_BOOTS, FArmorMaterials.FUR_LINED_CHAINMAIL_ASSET, BOOTS, false);
        itemModelGenerator.registerArmor(FItems.ARMORED_ICE_SKATES, FArmorMaterials.FUR_LINED_CHAINMAIL_ASSET, BOOTS, false);

        itemModelGenerator.register(FItems.FUR_PADDING);
        itemModelGenerator.register(FItems.FUR_UPGRADE_TEMPLATE);
        itemModelGenerator.register(FItems.ICE_SKATE_UPGRADE_TEMPLATE);
        itemModelGenerator.register(FItems.SNOW_MAN_ARMOR_TRIM_SMITHING_TEMPLATE);
        itemModelGenerator.register(FItems.FROSTY_ARMOR_TRIM_SMITHING_TEMPLATE);
        itemModelGenerator.register(FItems.GLACIAL_ARMOR_TRIM_SMITHING_TEMPLATE);

        itemModelGenerator.register(FItems.GLACIAL_HEART);
        itemModelGenerator.register(FItems.INERT_FROSTOLOGY_CLOAK);
        itemModelGenerator.register(FItems.FROSTOLOGY_CLOAK);
        itemModelGenerator.register(FItems.FROZEN_ROD);

        itemModelGenerator.register(FItems.POLAR_BEAR_FUR_TUFT);
        itemModelGenerator.register(FItems.WOLF_FUR_TUFT);
        itemModelGenerator.register(FItems.OCELOT_FUR_TUFT);

        itemModelGenerator.register(FItems.ICICLE);

        itemModelGenerator.register(FItems.COLD_SUN_LICHEN);
        itemModelGenerator.register(FItems.COOL_SUN_LICHEN);
        itemModelGenerator.register(FItems.WARM_SUN_LICHEN);
        itemModelGenerator.register(FItems.HOT_SUN_LICHEN);

        itemModelGenerator.register(FItems.GLACIAL_ARROW);
        itemModelGenerator.register(FItems.FROZEN_TORCH);
        itemModelGenerator.register(FItems.PACKED_SNOW);
        itemModelGenerator.register(FItems.PACKED_SNOWBALL);

        itemModelGenerator.register(FItems.PACKED_SNOW_BRICK_STAIRS);
        itemModelGenerator.register(FItems.PACKED_SNOW_BRICK_SLAB);
        itemModelGenerator.register(FItems.PACKED_SNOW_BRICK_WALL);

        itemModelGenerator.register(FItems.ICE_PANE);
        itemModelGenerator.register(FItems.CUT_PACKED_ICE_STAIRS);
        itemModelGenerator.register(FItems.CUT_PACKED_ICE_SLAB);
        itemModelGenerator.register(FItems.CUT_PACKED_ICE_WALL);
        itemModelGenerator.register(FItems.CUT_BLUE_ICE_STAIRS);
        itemModelGenerator.register(FItems.CUT_BLUE_ICE_SLAB);
        itemModelGenerator.register(FItems.CUT_BLUE_ICE_WALL);
        itemModelGenerator.register(FItems.BRITTLE_ICE);

        itemModelGenerator.register(FItems.SNOWFLAKE_BANNER_PATTERN);
        itemModelGenerator.register(FItems.ICICLE_BANNER_PATTERN);
        itemModelGenerator.register(FItems.FROSTOLOGY_BANNER_PATTERN);
        itemModelGenerator.register(FItems.ICY_TRIAL_SPAWNER);
        itemModelGenerator.register(FItems.ICY_VAULT);
        itemModelGenerator.register(FItems.CASTLE_KEY);
        itemModelGenerator.register(FItems.OMINOUS_CASTLE_KEY);

        itemModelGenerator.registerSpawnEgg(FItems.FROSTOLOGER_SPAWN_EGG, 0x473882, 0xBEB2EB);
        itemModelGenerator.registerSpawnEgg(FItems.CHILLAGER_SPAWN_EGG, 0x3432A8, 0xA2CCFC);
        itemModelGenerator.registerSpawnEgg(FItems.BITER_SPAWN_EGG, 0xEBFEFF, 0x2E64C3);
    }

    private void registerFrostWand(Item item, ItemModelGenerator itemModelGenerator) {
        ItemModel.Unbaked sprite = ItemModels.basic(itemModelGenerator.upload(item, Models.GENERATED));

        ItemModel.Unbaked inHand = ItemModels.special(
                ModelIds.getItemSubModelId(item, "_in_hand"),
                new FrostWandItemRenderer.Unbaked()
        );

        itemModelGenerator.output.accept(item, ItemModelGenerator.createModelWithInHandVariant(sprite, inHand));
    }
}