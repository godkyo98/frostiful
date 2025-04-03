package com.github.thedeathlycow.frostiful;

import com.github.thedeathlycow.frostiful.client.FrostifulModelLoadingPlugin;
import com.github.thedeathlycow.frostiful.client.FrozenHeartsOverlay;
import com.github.thedeathlycow.frostiful.client.network.PointWindSpawnPacketListener;
import com.github.thedeathlycow.frostiful.client.registry.FCutouts;
import com.github.thedeathlycow.frostiful.client.registry.FEntityModelLayers;
import com.github.thedeathlycow.frostiful.client.registry.FEntityRenderers;
import com.github.thedeathlycow.frostiful.client.registry.FParticleFactoryRegistry;
import com.github.thedeathlycow.frostiful.client.render.FrostWandItemRenderer;
import com.github.thedeathlycow.frostiful.compat.FoodIntegration;
import com.github.thedeathlycow.frostiful.server.network.PointWindSpawnPacket;
import com.github.thedeathlycow.thermoo.api.client.StatusBarOverlayRenderEvents;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.render.item.model.special.SpecialModelTypes;

@Environment(EnvType.CLIENT)
public class FrostifulClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FCutouts.initialize();
        FParticleFactoryRegistry.initialize();
        FEntityModelLayers.initialize();
        FEntityRenderers.initialize();

        SpecialModelTypes.ID_MAPPER.put(Frostiful.id("frost_wand"), FrostWandItemRenderer.Unbaked.CODEC);
        ModelLoadingPlugin.register(new FrostifulModelLoadingPlugin());

        ClientPlayNetworking.registerGlobalReceiver(
                PointWindSpawnPacket.PACKET_ID,
                new PointWindSpawnPacketListener()
        );
        StatusBarOverlayRenderEvents.AFTER_HEALTH_BAR.register(FrozenHeartsOverlay::afterHealthBar);
        StatusBarOverlayRenderEvents.AFTER_MOUNT_HEALTH_BAR.register(FrozenHeartsOverlay::afterMountHealthBar);
        ItemTooltipCallback.EVENT.register(FoodIntegration::appendWarmthTooltip);

        Frostiful.LOGGER.info("Initialized Frostiful client!");
    }
}
