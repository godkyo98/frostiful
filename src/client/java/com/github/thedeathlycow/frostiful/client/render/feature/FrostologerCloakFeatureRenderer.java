package com.github.thedeathlycow.frostiful.client.render.feature;

import com.github.thedeathlycow.frostiful.client.model.FrostologerEntityModel;
import com.github.thedeathlycow.frostiful.client.registry.FEntityModelLayers;
import com.github.thedeathlycow.frostiful.client.render.state.FrostologerEntityRenderState;
import com.github.thedeathlycow.frostiful.entity.frostologer.FrostologerEntity;
import com.github.thedeathlycow.frostiful.item.cloak.AbstractFrostologyCloakItem;
import com.github.thedeathlycow.frostiful.registry.FItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.equipment.EquipmentModelLoader;
import net.minecraft.client.render.entity.feature.CapeFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.PlayerCapeModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.EquipmentModel;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

@Environment(EnvType.CLIENT)
public class FrostologerCloakFeatureRenderer extends FeatureRenderer<FrostologerEntityRenderState, FrostologerEntityModel<FrostologerEntityRenderState>> {
    private final FrostologerEntityModel<FrostologerEntityRenderState> model;
    private final EquipmentModelLoader equipmentModelLoader;

    public FrostologerCloakFeatureRenderer(
            FeatureRendererContext<FrostologerEntityRenderState, FrostologerEntityModel<FrostologerEntityRenderState>> featureRendererContext,
            EntityModelLoader modelLoader,
            EquipmentModelLoader equipmentModelLoader
    ) {
        super(featureRendererContext);
        this.equipmentModelLoader = equipmentModelLoader;
        this.model = new FrostologerEntityModel<>(modelLoader.getModelPart(FEntityModelLayers.FROSTOLOGER));
    }

    @Override
    public void render(
            MatrixStack matrixStack,
            VertexConsumerProvider vertexConsumerProvider,
            int light,
            FrostologerEntityRenderState state,
            float limbAngle,
            float limbDistance
    ) {
        if (!state.invisible && state.capeVisible) {
            matrixStack.push();
            matrixStack.translate(0.0, 0.0, 3f / 16f);

            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(
                    RenderLayer.getEntitySolid(AbstractFrostologyCloakItem.MODEL_TEXTURE_ID)
            );
            this.model.setAngles(state);
            this.model.renderCloak(matrixStack, vertexConsumer, light, OverlayTexture.DEFAULT_UV);

            matrixStack.pop();
        }
    }
}
