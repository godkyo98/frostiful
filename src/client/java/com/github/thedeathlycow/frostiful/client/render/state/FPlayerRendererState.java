package com.github.thedeathlycow.frostiful.client.render.state;

public interface FPlayerRendererState {
    default boolean frostiful$wearingFrostologyCape() {
        throw new AssertionError("Implemented in mixin");
    }

    default void frostiful$wearingFrostologyCape(boolean value) {
        throw new AssertionError("Implemented in mixin");
    }
}