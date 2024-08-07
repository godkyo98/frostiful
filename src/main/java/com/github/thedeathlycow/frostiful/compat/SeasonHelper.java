package com.github.thedeathlycow.frostiful.compat;

import com.github.thedeathlycow.frostiful.survival.BiomeCategory;
import com.github.thedeathlycow.thermoo.api.season.ThermooSeason;
import org.jetbrains.annotations.Nullable;

public class SeasonHelper {

    /**
     * Adjusts biome categories for their season.
     *
     * @param season The current season
     * @param normalCategory The normal category of the biome if no season mod was enabled.
     * @return Returns the biome category, adjusted for the season.
     */
    public static BiomeCategory getSeasonallyShiftedBiomeCategory(
            @Nullable ThermooSeason season,
            BiomeCategory normalCategory
    ) {
        if (season == ThermooSeason.SUMMER && normalCategory == BiomeCategory.FREEZING) {
            return BiomeCategory.COLD;
        }
        return normalCategory;
    }

}
