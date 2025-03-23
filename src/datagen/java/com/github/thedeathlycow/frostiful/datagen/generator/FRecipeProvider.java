package com.github.thedeathlycow.frostiful.datagen.generator;

import com.github.thedeathlycow.frostiful.registry.FItems;
import com.github.thedeathlycow.frostiful.registry.tag.FItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeGenerator;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class FRecipeProvider extends FabricRecipeProvider {
    public FRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registryLookup, RecipeExporter exporter) {
        return new RecipeGenerator(registryLookup, exporter) {
            @Override
            public void generate() {
                createShaped(RecipeCategory.BUILDING_BLOCKS, FItems.BRITTLE_ICE)
                        .criterion(hasItem(FItems.ICICLE), conditionsFromTag(FItemTags.ICICLES))
                        .pattern("##")
                        .pattern("##")
                        .input('#', FItems.ICICLE)
                        .offerTo(exporter);

                offerCutBlueIceRecipes();
                offerCutPackedIceRecipes();

            }

            private void offerCutBlueIceRecipes() {
                createShaped(RecipeCategory.BUILDING_BLOCKS, FItems.CUT_BLUE_ICE, 4)
                        .criterion(hasItem(Items.BLUE_ICE), conditionsFromItem(Items.BLUE_ICE))
                        .pattern("##")
                        .pattern("##")
                        .input('#', Items.BLUE_ICE)
                        .offerTo(exporter);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, FItems.CUT_BLUE_ICE, Items.BLUE_ICE, 4);

                createStairsRecipe(FItems.CUT_BLUE_ICE_STAIRS, Ingredient.ofItem(FItems.CUT_BLUE_ICE))
                        .criterion(hasItem(FItems.CUT_BLUE_ICE), conditionsFromItem(FItems.CUT_BLUE_ICE))
                        .offerTo(exporter);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, FItems.CUT_BLUE_ICE_STAIRS, FItems.CUT_BLUE_ICE);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, FItems.CUT_BLUE_ICE_STAIRS, Items.BLUE_ICE, 4);

                offerSlabRecipe(RecipeCategory.DECORATIONS, FItems.CUT_BLUE_ICE_SLAB, FItems.CUT_BLUE_ICE);
                offerStonecuttingRecipe(RecipeCategory.DECORATIONS, FItems.CUT_BLUE_ICE_SLAB, FItems.CUT_BLUE_ICE, 2);
                offerStonecuttingRecipe(RecipeCategory.DECORATIONS, FItems.CUT_BLUE_ICE_SLAB, Items.BLUE_ICE, 8);

                offerWallRecipe(RecipeCategory.DECORATIONS, FItems.CUT_BLUE_ICE_WALL, FItems.CUT_BLUE_ICE);
                offerStonecuttingRecipe(RecipeCategory.DECORATIONS, FItems.CUT_BLUE_ICE_WALL, FItems.CUT_BLUE_ICE);
                offerStonecuttingRecipe(RecipeCategory.DECORATIONS, FItems.CUT_BLUE_ICE_WALL, Items.BLUE_ICE, 4);
            }

            private void offerCutPackedIceRecipes() {
                createShaped(RecipeCategory.BUILDING_BLOCKS, FItems.CUT_PACKED_ICE, 4)
                        .criterion(hasItem(Items.PACKED_ICE), conditionsFromItem(Items.PACKED_ICE))
                        .pattern("##")
                        .pattern("##")
                        .input('#', Items.PACKED_ICE)
                        .offerTo(exporter);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, FItems.CUT_PACKED_ICE, Items.PACKED_ICE, 4);

                createStairsRecipe(FItems.CUT_PACKED_ICE_STAIRS, Ingredient.ofItem(FItems.CUT_PACKED_ICE))
                        .criterion(hasItem(FItems.CUT_PACKED_ICE), conditionsFromItem(FItems.CUT_PACKED_ICE))
                        .offerTo(exporter);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, FItems.CUT_PACKED_ICE_STAIRS, FItems.CUT_PACKED_ICE);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, FItems.CUT_PACKED_ICE_STAIRS, Items.PACKED_ICE, 4);

                offerSlabRecipe(RecipeCategory.DECORATIONS, FItems.CUT_PACKED_ICE_SLAB, FItems.CUT_PACKED_ICE);
                offerStonecuttingRecipe(RecipeCategory.DECORATIONS, FItems.CUT_PACKED_ICE_SLAB, FItems.CUT_PACKED_ICE, 2);
                offerStonecuttingRecipe(RecipeCategory.DECORATIONS, FItems.CUT_PACKED_ICE_SLAB, Items.PACKED_ICE, 8);

                offerWallRecipe(RecipeCategory.DECORATIONS, FItems.CUT_PACKED_ICE_WALL, FItems.CUT_PACKED_ICE);
                offerStonecuttingRecipe(RecipeCategory.DECORATIONS, FItems.CUT_PACKED_ICE_WALL, FItems.CUT_PACKED_ICE);
                offerStonecuttingRecipe(RecipeCategory.DECORATIONS, FItems.CUT_PACKED_ICE_WALL, Items.PACKED_ICE, 4);
            }
        };
    }

    @Override
    public String getName() {
        return "FrostifulRecipeGenerator";
    }
}