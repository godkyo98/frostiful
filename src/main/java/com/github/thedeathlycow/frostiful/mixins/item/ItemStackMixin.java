package com.github.thedeathlycow.frostiful.mixins.item;

import com.github.thedeathlycow.frostiful.item.attribute.ResistanceComponentBuilder;
import net.minecraft.component.ComponentMapImpl;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Inject(
            method = "<init>(Lnet/minecraft/item/ItemConvertible;ILnet/minecraft/component/ComponentMapImpl;)V",
            at = @At("TAIL")
    )
    private void onInit(ItemConvertible item, int count, ComponentMapImpl components, CallbackInfo ci) {
        ResistanceComponentBuilder.applyLegacyArmorMaterialTags((ItemStack) (Object) this);
    }
}