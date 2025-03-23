package com.github.thedeathlycow.frostiful.client.mixin.entity_renderer;

import com.github.thedeathlycow.frostiful.client.render.state.FBipedRenderState;
import com.github.thedeathlycow.frostiful.client.render.state.FPlayerRendererState;
import com.github.thedeathlycow.frostiful.item.cloak.AbstractFrostologyCloakItem;
import com.github.thedeathlycow.frostiful.registry.tag.FItemTags;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.entity.EquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {
    @Inject(
            method = "updateRenderState(Lnet/minecraft/client/network/AbstractClientPlayerEntity;Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;F)V",
            at = @At("TAIL")
    )
    private void updateRenderState(AbstractClientPlayerEntity entity, PlayerEntityRenderState state, float tickDelta, CallbackInfo ci) {
        boolean wearingFrostologyCape = AbstractFrostologyCloakItem.isWearing(entity, stack -> stack.isIn(FItemTags.FROSTOLOGY_CLOAKS));
        boolean wearingSkates = entity.getEquippedStack(EquipmentSlot.FEET).isIn(FItemTags.ICE_SKATES);

        FPlayerRendererState playerState = ((FPlayerRendererState) state);
        FBipedRenderState bipedState = ((FBipedRenderState) state);

        bipedState.frostiful$wearingIceSkates(wearingSkates);
        playerState.frostiful$wearingFrostologyCape(wearingFrostologyCape);
    }
}