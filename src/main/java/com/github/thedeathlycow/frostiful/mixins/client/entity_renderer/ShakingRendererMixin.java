package com.github.thedeathlycow.frostiful.mixins.client.entity_renderer;

import com.github.thedeathlycow.frostiful.entity.FreezableEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
@Environment(EnvType.CLIENT)
public class ShakingRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {

    @Inject(
            method = "isShaking",
            at = @At("TAIL"),
            cancellable = true
    )
    private void shakeWhenFreezing(T entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof FreezableEntity freezable) {
            if (freezable.frostiful$isFrozen()) {
                cir.setReturnValue(true);
            }
        }
    }
}
