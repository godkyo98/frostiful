package com.github.thedeathlycow.frostiful.mixins.entity;

import com.github.thedeathlycow.frostiful.config.group.IcicleConfigGroup;
import com.github.thedeathlycow.frostiful.util.survival.FrostHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.Consumer;

@Mixin(FallingBlockEntity.class)
public class FallingIcicleMixin {

    @ModifyArg(
            method = "handleFallDamage",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;forEach(Ljava/util/function/Consumer;)V"
            ),
            index = 0
    )
    private Consumer<Entity> freezeVictimsOnFall(Consumer<Entity> par1) {
        return par1.andThen((entity) -> {
            if (entity instanceof LivingEntity livingEntity) {
                FrostHelper.addLivingFrost(livingEntity, IcicleConfigGroup.ICICLE_COLLISION_FREEZE_AMOUNT.getValue());
            }
        });
    }

}
