package com.github.thedeathlycow.frostiful.client.render.entity;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.entity.ThrownIcicleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.client.render.entity.state.ProjectileEntityRenderState;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ThrownIcicleEntityRenderer extends ProjectileEntityRenderer<ThrownIcicleEntity, ProjectileEntityRenderState> {

    public static final Identifier TEXTURE = Frostiful.id("textures/entity/projectiles/thrown_icicle.png");


    public ThrownIcicleEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public ProjectileEntityRenderState createRenderState() {
        return new ProjectileEntityRenderState();
    }


    @Override
    public Identifier getTexture(ProjectileEntityRenderState entity) {
        return TEXTURE;
    }
}
