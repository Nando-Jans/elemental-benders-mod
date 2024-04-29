package com.example.entity.renderer;

import com.example.ElementalBendersMod;
import com.example.entity.mob.bender.fire.FireBenderEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.util.Identifier;

public class FireBenderEntityRenderer extends MobEntityRenderer<FireBenderEntity, PlayerEntityModel<FireBenderEntity>> {

    public FireBenderEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new PlayerEntityModel<>(context.getPart(EntityModelLayers.PLAYER), false), 0.5F);
    }

    @Override
    public Identifier getTexture(FireBenderEntity entity) {
        return new Identifier(ElementalBendersMod.MOD_ID, "textures/entity/fire_bender.png");
    }
}
