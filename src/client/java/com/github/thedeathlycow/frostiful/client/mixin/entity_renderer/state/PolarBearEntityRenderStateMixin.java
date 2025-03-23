package com.github.thedeathlycow.frostiful.client.mixin.entity_renderer.state;

import com.github.thedeathlycow.frostiful.client.render.state.FPolarBearEntityRenderState;
import net.minecraft.client.render.entity.state.PolarBearEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PolarBearEntityRenderState.class)
public class PolarBearEntityRenderStateMixin implements FPolarBearEntityRenderState {
    private boolean frostiful$wasSheared = false;

    @Override
    @Unique
    public boolean frostiful$wasSheared() {
        return this.frostiful$wasSheared;
    }

    @Override
    @Unique
    public void frostiful$wasSheared(boolean value) {
        this.frostiful$wasSheared = value;
    }
}