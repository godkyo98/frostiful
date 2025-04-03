package com.github.thedeathlycow.frostiful.client.render.entity;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.client.registry.FEntityModelLayers;
import com.github.thedeathlycow.frostiful.entity.ChillagerEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.IllagerEntityRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.client.render.entity.state.IllagerEntityRenderState;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ChillagerEntityRenderer extends IllagerEntityRenderer<ChillagerEntity, IllagerEntityRenderState> {

    private static final Identifier TEXTURE = Frostiful.id("textures/entity/illager/chillager.png");


    public ChillagerEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new IllagerEntityModel<>(context.getPart(FEntityModelLayers.CHILLAGER)), 0.5F);
        this.addFeature(new HeldItemFeatureRenderer<>(this));

        this.model.getHat().visible = true;
    }

    @Override
    public IllagerEntityRenderState createRenderState() {
        return new IllagerEntityRenderState();
    }

    @Override
    public Identifier getTexture(IllagerEntityRenderState state) {
        return TEXTURE;
    }
}
