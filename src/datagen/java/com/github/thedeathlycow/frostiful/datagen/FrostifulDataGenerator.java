package com.github.thedeathlycow.frostiful.datagen;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import org.jetbrains.annotations.Nullable;

public class FrostifulDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {

    }

    @Override
    @Nullable
    public String getEffectiveModId() {
        return Frostiful.MODID;
    }
}