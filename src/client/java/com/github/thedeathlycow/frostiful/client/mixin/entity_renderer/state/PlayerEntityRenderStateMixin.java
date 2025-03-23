package com.github.thedeathlycow.frostiful.client.mixin.entity_renderer.state;

import com.github.thedeathlycow.frostiful.client.render.state.FPlayerRendererState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerEntityRenderState.class)
public class PlayerEntityRenderStateMixin implements FPlayerRendererState {
    @Unique
    private boolean frostiful$wearingFrostologyCape = false;

    @Override
    public boolean frostiful$wearingFrostologyCape() {
        return frostiful$wearingFrostologyCape;
    }

    @Override
    public void frostiful$wearingFrostologyCape(boolean value) {
        this.frostiful$wearingFrostologyCape = value;
    }
}