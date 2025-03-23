package com.github.thedeathlycow.frostiful.client.mixin.entity_renderer;

import com.github.thedeathlycow.frostiful.client.render.state.FBipedRenderState;
import com.github.thedeathlycow.frostiful.item.cloak.AbstractFrostologyCloakItem;
import com.github.thedeathlycow.frostiful.registry.tag.FItemTags;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityRenderer.class)
public abstract class BipedEntityRendererMixin<T extends MobEntity, S extends BipedEntityRenderState, M extends BipedEntityModel<S>> {
    @Inject(
            method = "updateRenderState(Lnet/minecraft/entity/mob/MobEntity;Lnet/minecraft/client/render/entity/state/BipedEntityRenderState;F)V",
            at = @At("TAIL")
    )
    private void updateIceSkateRenderState(T mobEntity, S bipedEntityRenderState, float tickDelta, CallbackInfo ci) {
        boolean wearingSkates = mobEntity.getEquippedStack(EquipmentSlot.FEET).isIn(FItemTags.ICE_SKATES);

        FBipedRenderState fState = ((FBipedRenderState) bipedEntityRenderState);

        fState.frostiful$wearingIceSkates(wearingSkates);
    }
}