package com.example.entity;

import com.example.entity.bending.fragments.fire.lightning.LightningAttackEntity;
import com.example.entity.bending.fragments.fire.trace.FireTraceAttackEntity;
import com.example.entity.bending.fragments.fire.wave.FireWaveAttackEntity;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.util.Identifier;

public class ModEntityRenderer {

    public static void registerEntityRenderers() {
        EntityRendererRegistry.register( ModEntities.FIRE_TRACE_ATTACK, (context) ->
                new EntityRenderer<>(context) {

                    @Override
                    public Identifier getTexture(FireTraceAttackEntity entity) {
                        return null;
                    }
                }
        );
        EntityRendererRegistry.register( ModEntities.FIRE_WAVE_ATTACK, (context) ->
                new EntityRenderer<>(context) {

                    @Override
                    public Identifier getTexture(FireWaveAttackEntity entity) {
                        return null;
                    }
                }
        );
        EntityRendererRegistry.register( ModEntities.LIGHTNING_ATTACK, (context) ->
                new EntityRenderer<>(context) {

                    @Override
                    public Identifier getTexture(LightningAttackEntity entity) {
                        return null;
                    }
                }
        );
    }
}
