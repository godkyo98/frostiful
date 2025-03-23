package com.github.thedeathlycow.frostiful.client.mixin.entity_renderer.state;

import com.github.thedeathlycow.frostiful.client.render.state.FBipedRenderState;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BipedEntityRenderState.class)
public class BipedEntityRenderStateMixin implements FBipedRenderState {
    @Unique
    private boolean frostiful$wearingIceSkates = false;

    @Override
    @Unique
    public boolean frostiful$wearingIceSkates() {
        return this.frostiful$wearingIceSkates;
    }

    @Override
    public void frostiful$wearingIceSkates(boolean value) {
        this.frostiful$wearingIceSkates = value;
    }
}