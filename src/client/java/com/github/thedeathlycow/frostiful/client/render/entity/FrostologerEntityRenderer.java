package com.github.thedeathlycow.frostiful.client.render.entity;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.client.model.FrostologerEntityModel;
import com.github.thedeathlycow.frostiful.client.registry.FEntityModelLayers;
import com.github.thedeathlycow.frostiful.client.render.feature.FrostLayer;
import com.github.thedeathlycow.frostiful.client.render.feature.FrostologerCloakFeatureRenderer;
import com.github.thedeathlycow.frostiful.client.render.feature.FrostologerEyesFeatureRenderer;
import com.github.thedeathlycow.frostiful.client.render.feature.FrostologerFrostFeatureRenderer;
import com.github.thedeathlycow.frostiful.client.render.state.FrostologerEntityRenderState;
import com.github.thedeathlycow.frostiful.entity.frostologer.FrostologerEntity;
import com.github.thedeathlycow.frostiful.registry.FItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class FrostologerEntityRenderer extends MobEntityRenderer<FrostologerEntity, FrostologerEntityRenderState, FrostologerEntityModel<FrostologerEntityRenderState>> {


    private static final Identifier TEXTURE = Frostiful.id("textures/entity/illager/frostologer.png");

    public FrostologerEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new FrostologerEntityModel<>(context.getPart(FEntityModelLayers.FROSTOLOGER)), 0.5F);

        this.addFeature(new HeadFeatureRenderer<>(this, context.getModelLoader(), context.getItemRenderer()));
        this.addFeature(new HeldItemFeatureRenderer<>(this, context.getItemRenderer()));
        this.addFeature(new FrostologerCloakFeatureRenderer(this, context.getModelLoader(), context.getEquipmentModelLoader()));
        this.addFeature(
                new FrostologerEyesFeatureRenderer<>(
                        this,
                        Frostiful.id("textures/entity/illager/frostologer/glow.png")
                )
        );
        this.addFeature(new FrostologerFrostFeatureRenderer(this));
    }

    @Override
    public FrostologerEntityRenderState createRenderState() {
        return new FrostologerEntityRenderState();
    }

    @Override
    public void updateRenderState(FrostologerEntity frostologer, FrostologerEntityRenderState state, float tickDelta) {
        super.updateRenderState(frostologer, state, tickDelta);
        state.usingFrostWand = frostologer.isUsingFrostWand();
        state.temperatureScale = frostologer.thermoo$getTemperatureScale();
        state.capeVisible = frostologer.getEquippedStack(EquipmentSlot.CHEST).isOf(FItems.FROSTOLOGY_CLOAK);
        state.rgColourMul = (0.625f * (state.temperatureScale + 1f)) + 0.5f;
        state.frostLayer = FrostLayer.fromFrostologer(frostologer);
        state.glowingEyes = frostologer.isAtMaxPower();

        if (state.capeVisible) {
            updateCape(frostologer, state, tickDelta);
        }
    }

    //    @Override
//    protected void scale(FrostologerEntity frostologerEntity, MatrixStack matrixStack, float amount) {
//        float scale = 15f / 16f;
//        matrixStack.scale(scale, scale, scale);
//    }

    @Override
    public Identifier getTexture(FrostologerEntityRenderState pillagerEntity) {
        return TEXTURE;
    }

    private static void updateCape(FrostologerEntity frostologer, FrostologerEntityRenderState state, float tickDelta) {
        double deltaX = MathHelper.lerp(tickDelta, frostologer.prevCapeX, frostologer.capeX) - MathHelper.lerp(tickDelta, frostologer.prevX, frostologer.getX());
        double deltaY = MathHelper.lerp(tickDelta, frostologer.prevCapeY, frostologer.capeY) - MathHelper.lerp(tickDelta, frostologer.prevY, frostologer.getY());
        double deltaZ = MathHelper.lerp(tickDelta, frostologer.prevCapeZ, frostologer.capeZ) - MathHelper.lerp(tickDelta, frostologer.prevZ, frostologer.getZ());

        float bodyYaw = MathHelper.lerpAngleDegrees(tickDelta, frostologer.prevBodyYaw, frostologer.bodyYaw);
        double sinYaw = MathHelper.sin(bodyYaw * (float)(Math.PI / 180.0));
        double cosYaw = -MathHelper.cos(bodyYaw * (float)(Math.PI / 180.0));

        state.capePitch = (float)deltaY * 10.0f;
        state.capePitch = MathHelper.clamp(state.capePitch, -6.0f, 32.0f);

        state.capeSwing = (float)(deltaX * sinYaw + deltaZ * cosYaw) * 100.0f;
        state.capeSwing = MathHelper.clamp(state.capeSwing, 0.0f, 150.0f);

        state.capeStrafe = (float)(deltaX * cosYaw - deltaZ * sinYaw) * 100.0f;
        state.capeStrafe = MathHelper.clamp(state.capeStrafe, -20.0f, 20.0f);

        if (state.sneaking) {
            state.capePitch += 25.0F;
        }

//        float stride = MathHelper.lerp(tickDelta, frostologer.prevStrideDistance, frostologer.strideDistance);
//        float distanceMoved = MathHelper.lerp(tickDelta, frostologer.prevHorizontalSpeed, frostologer.horizontalSpeed);
//        state.capePitch += MathHelper.sin(distanceMoved * 6.0f) * 32.0f * stride;
    }
}
