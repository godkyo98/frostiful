package com.github.thedeathlycow.frostiful.datafix;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.data.validate.StructureValidatorProvider;
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

public final class StructureUpdateHelper {
    // relative to the /run/structure_update dir
    private static final Path STRUCTURE_PATH = Paths.get("./structure");
    private static final Path OUT_PATH = Paths.get("./generated");

    public static void initialize() {
        if (FabricLoader.getInstance().isDevelopmentEnvironment() && System.getProperty("frostiful.update-structures") != null) {
            ServerLifecycleEvents.SERVER_STARTED.register(server -> {
                updateAllStructures();
                Frostiful.LOGGER.info("All structures updated! :)");
                server.stop(false);
            });
        }
    }

    private static void updateAllStructures() {
        if (!FabricLoader.getInstance().isDevelopmentEnvironment()) {
            throw new IllegalStateException("Structures may only be updated in a dev environment!");
        }

        try (Stream<Path> paths = Files.walk(STRUCTURE_PATH)) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".nbt"))
                    .forEach(StructureUpdateHelper::updateStructureFile);
        } catch (IOException e) {
            Frostiful.LOGGER.error("Unable to read structures", e);
        }
    }

    private static void updateStructureFile(Path path) {
        try (InputStream in = Files.newInputStream(path)) {
            NbtCompound oldNbt = NbtIo.readCompressed(in, NbtSizeTracker.ofUnlimitedBytes());

            NbtCompound updatedNbt = StructureValidatorProvider.update(path.toString(), oldNbt);

            Path outPath = OUT_PATH.resolve(path).normalize();
            Files.createDirectories(outPath.getParent());
            try (OutputStream out = Files.newOutputStream(outPath)) {
                NbtIo.writeCompressed(updatedNbt, out);
                Frostiful.LOGGER.info("Updated structure {}", path);
            }
        } catch (IOException e) {
            Frostiful.LOGGER.error("Failed to update structure file {}", path);
        }
    }

    private StructureUpdateHelper() {

    }
}