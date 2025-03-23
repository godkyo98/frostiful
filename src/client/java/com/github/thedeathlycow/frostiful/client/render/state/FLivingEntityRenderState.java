package com.github.thedeathlycow.frostiful.client.render.state;

public interface FLivingEntityRenderState {
    default int frostiful$getRootedTicks() {
        throw new AssertionError("Implemented in mixin");
    }

    default void frostiful$setRootedTicks(int value) {
        throw new AssertionError("Implemented in mixin");
    }
}
