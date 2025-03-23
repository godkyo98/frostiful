package com.github.thedeathlycow.frostiful.client.mixin.entity_renderer.state;

import com.github.thedeathlycow.frostiful.client.render.state.RootedEntityRenderState;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LivingEntityRenderState.class)
public class LivingEntityRenderStateMixin implements RootedEntityRenderState {
    @Unique
    private int frostiful$rootedTicks = 0;

    @Override
    @Unique
    public int frostiful$getRootedTicks() {
        return 0;
    }

    @Override
    public void frostiful$setRootedTicks(int value) {
        this.frostiful$rootedTicks = value;
    }


}