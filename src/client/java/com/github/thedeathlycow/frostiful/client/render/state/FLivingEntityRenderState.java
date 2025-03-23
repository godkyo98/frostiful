package com.github.thedeathlycow.frostiful.client.render.state;

public interface FLivingEntityRenderState {
    default boolean frostiful$isRooted() {
        throw new AssertionError("Implemented in mixin");
    }

    default void frostiful$isRooted(boolean value) {
        throw new AssertionError("Implemented in mixin");
    }
}
