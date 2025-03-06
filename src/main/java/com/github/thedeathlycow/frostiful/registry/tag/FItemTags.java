package com.github.thedeathlycow.frostiful.registry.tag;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public final class FItemTags {

    public static final TagKey<Item> FUR = register("fur");

    public static final TagKey<Item> POWDER_SNOW_WALKABLE = register("powder_snow_walkable");

    public static final TagKey<Item> SUN_LICHENS = register("sun_lichens");

    public static final TagKey<Item> ICE_SKATES = register("ice_skates");

    public static final TagKey<Item> WARM_FOODS = register("warm_foods");

    public static final TagKey<Item> FROSTOLOGY_CLOAKS = register("frostology_cloaks");

    public static final TagKey<Item> IS_VERY_PROTECTIVE_FROST_RESISTANCE = register("is_frost_resistant/very_protective");
    public static final TagKey<Item> IS_PROTECTIVE_FROST_RESISTANCE = register("is_frost_resistant/protective");
    public static final TagKey<Item> IS_HARMFUL_FROST_RESISTANCE = register("is_frost_resistant/harmful");
    public static final TagKey<Item> IS_VERY_HARMFUL_FROST_RESISTANCE = register("is_frost_resistant/very_harmful");

    private static TagKey<Item> register(String id) {
        return TagKey.of(RegistryKeys.ITEM, Frostiful.id(id));
    }

}
