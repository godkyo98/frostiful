package com.github.thedeathlycow.frostiful.client.render.state;

public interface IceSkateRenderState {
    default boolean frostiful$wearingIceSkates() {
        throw new AssertionError("Implemented in mixin");
    }

    default void frostiful$setWearingIceSkates(boolean value) {
        throw new AssertionError("Implemented in mixin");
    }
}
