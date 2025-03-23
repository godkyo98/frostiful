package com.github.thedeathlycow.frostiful.client.mixin.entity_renderer;

import com.github.thedeathlycow.frostiful.client.render.state.FBipedRenderState;
import com.github.thedeathlycow.frostiful.registry.tag.FItemTags;
import net.minecraft.client.render.entity.ArmorStandEntityRenderer;
import net.minecraft.client.render.entity.state.ArmorStandEntityRenderState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.decoration.ArmorStandEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorStandEntityRenderer.class)
public class ArmorStandEntityRendererMixin {
    @Inject(
            method = "updateRenderState(Lnet/minecraft/entity/decoration/ArmorStandEntity;Lnet/minecraft/client/render/entity/state/ArmorStandEntityRenderState;F)V",
            at = @At("TAIL")
    )
    private void updateRenderState(ArmorStandEntity entity, ArmorStandEntityRenderState state, float tickDelta, CallbackInfo ci) {
        boolean wearingSkates = entity.getEquippedStack(EquipmentSlot.FEET).isIn(FItemTags.ICE_SKATES);

        FBipedRenderState bipedState = ((FBipedRenderState) state);

        bipedState.frostiful$wearingIceSkates(wearingSkates);
    }
}