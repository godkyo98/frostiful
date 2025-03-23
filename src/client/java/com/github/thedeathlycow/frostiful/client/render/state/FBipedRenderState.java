package com.github.thedeathlycow.frostiful.client.render.state;

public interface FBipedRenderState {
    default boolean frostiful$wearingIceSkates() {
        throw new AssertionError("Implemented in mixin");
    }

    default void frostiful$wearingIceSkates(boolean value) {
        throw new AssertionError("Implemented in mixin");
    }

    default boolean frostiful$wearingFrostologyCape() {
        throw new AssertionError("Implemented in mixin");
    }

    default void frostiful$wearingFrostologyCape(boolean value) {
        throw new AssertionError("Implemented in mixin");
    }
}
