package com.github.thedeathlycow.frostiful.client.render.state;

import com.github.thedeathlycow.frostiful.client.render.feature.FrostLayer;
import net.minecraft.client.render.entity.state.IllagerEntityRenderState;

public class FrostologerEntityRenderState extends IllagerEntityRenderState {
    public boolean usingFrostWand = false;
    public boolean capeVisible = false;
    public float temperatureScale = 0f;
    public float rgColourMul = 0f;
    public FrostLayer frostLayer = FrostLayer.NONE;
    public boolean glowingEyes = false;
    public float capePitch = 0f;
    public float capeSwing = 0f;
    public float capeStrafe = 0f;
}