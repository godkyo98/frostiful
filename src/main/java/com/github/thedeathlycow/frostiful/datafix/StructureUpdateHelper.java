package com.github.thedeathlycow.frostiful.datafix;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.mojang.datafixers.DataFixer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.SharedConstants;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtSizeTracker;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class StructureUpdateHelper {
    // relative to the /run dir
    private static final Path STRUCTURE_PATH = Paths.get("../src/main/resources/data/frostiful/structure/");

    private static final int TARGET_VERSION = SharedConstants.getGameVersion().getSaveVersion().getId();

    private final DataFixer dataFixer;

    public StructureUpdateHelper(DataFixer dataFixer) {
        this.dataFixer = dataFixer;
    }

    public static void initialize() {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            var updater = new StructureUpdateHelper(server.getDataFixer());
            updater.updateAllStructures();
        });
    }

    public void updateAllStructures() {
        if (!FabricLoader.getInstance().isDevelopmentEnvironment()) {
            throw new IllegalStateException("Structures may only be updated in a dev environment!");
        }

        try (Stream<Path> paths = Files.walk(STRUCTURE_PATH)) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".nbt"))
                    .forEach(this::updateStructureFile);
        } catch (IOException e) {
            Frostiful.LOGGER.error("Unable to read structures: {}", e);
        }
    }

    private void updateStructureFile(Path path) {
        try (InputStream in = Files.newInputStream(path)) {
            NbtCompound tag = NbtIo.readCompressed(in, NbtSizeTracker.ofUnlimitedBytes());
            int oldVersion = tag.contains("DataVersion") ? tag.getInt("DataVersion") : TARGET_VERSION;

            NbtCompound updatedTag = DataFixTypes.STRUCTURE.update(this.dataFixer, tag, oldVersion, TARGET_VERSION);

            updatedTag.putInt("DataVersion", TARGET_VERSION);

            try (OutputStream out = Files.newOutputStream(path)) {
                NbtIo.writeCompressed(updatedTag, out);
            }
        } catch (IOException e) {
            Frostiful.LOGGER.error("Failed to update structure file {}: {}", path, e);
        }
    }
}