package com.github.thedeathlycow.frostiful.client.render.feature;

import com.github.thedeathlycow.frostiful.client.model.FrostologerEntityModel;
import com.github.thedeathlycow.frostiful.client.render.state.FrostologerEntityRenderState;
import com.github.thedeathlycow.frostiful.entity.frostologer.FrostologerEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class FrostologerFrostFeatureRenderer extends FeatureRenderer<FrostologerEntityRenderState, FrostologerEntityModel<FrostologerEntityRenderState>> {

    public FrostologerFrostFeatureRenderer(
            FeatureRendererContext<FrostologerEntityRenderState, FrostologerEntityModel<FrostologerEntityRenderState>> context
    ) {
        super(context);
    }

    @Override
    public void render(
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light,
            FrostologerEntityRenderState state,
            float limbAngle,
            float limbDistance
    ) {
        Identifier texture = state.frostLayer.getTexture();
        if (texture != null) {
            FeatureRenderer.renderModel(
                    this.getContextModel(),
                    texture,
                    matrices,
                    vertexConsumers,
                    light,
                    state,
                    -1
            );
        }
    }
}
