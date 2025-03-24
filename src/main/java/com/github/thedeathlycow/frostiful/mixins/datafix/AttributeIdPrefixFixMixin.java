package com.github.thedeathlycow.frostiful.mixins.datafix;

import com.github.thedeathlycow.frostiful.datafix.FAttributeIdPrefixFix;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.datafixer.fix.AttributeIdPrefixFix;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AttributeIdPrefixFix.class)
public class AttributeIdPrefixFixMixin {
    @ModifyReturnValue(
            method = "removePrefix",
            at = @At("TAIL")
    )
    private static String removePrefixForThermoo(String original) {
        return FAttributeIdPrefixFix.fixPrefixedAttributeIds(original);
    }
}