package com.github.thedeathlycow.frostiful.client.render.feature;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.client.model.IceSkateModel;
import com.github.thedeathlycow.frostiful.client.render.state.FBipedRenderState;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class IceSkateFeatureRenderer<
        S extends BipedEntityRenderState,
        M extends BipedEntityModel<S>,
        I extends IceSkateModel<S>
        > extends FeatureRenderer<S, M> {
    private final I model;

    private static final Identifier SKATE_TEXTURE = Frostiful.id("textures/entity/skates.png");

    public IceSkateFeatureRenderer(
            FeatureRendererContext<S, M> context,
            I model
    ) {
        super(context);
        this.model = model;
    }

    @Override
    public void render(
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light,
            S state,
            float limbAngle,
            float limbDistance
    ) {
        if (((FBipedRenderState) state).frostiful$wearingIceSkates()) {
            this.getContextModel().copyTransforms(model);
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getArmorCutoutNoCull(SKATE_TEXTURE));
            this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
        }
    }
}
