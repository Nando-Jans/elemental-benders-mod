package com.example.effect.fire;

import com.example.effect.ModStatusEffect;
import com.example.particle.ModParticles;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;

import java.util.Objects;

public class LightningBoltStatusEffect extends ModStatusEffect {

    public LightningBoltStatusEffect(StatusEffectCategory category) {
        super(category, 0x000000);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        super.applyUpdateEffect(entity, amplifier);
        // Spawn particles
        spawnParticles(entity, 2);

        // Get the time the entity has been affected by the status effect
        int duration = Objects.requireNonNull(entity.getStatusEffect(this)).getDuration();

        if (duration <= 1) {
            applyLightningBoltEffect(entity, amplifier);
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    private void applyLightningBoltEffect(LivingEntity entity, int amplifier) {
        // Lightning bolt effect logic
        for (int i = 0; i < amplifier; i++) {
            LightningEntity lightningEntity = new LightningEntity(EntityType.LIGHTNING_BOLT , entity.getWorld());
            lightningEntity.setPosition(entity.getX(), entity.getY(), entity.getZ());
            entity.getWorld().spawnEntity(lightningEntity);
        }
        spawnParticles(entity, 100);
        entity.removeStatusEffect(this);
    }

    @Override
    public Identifier getParticle() {
        return ModParticles.LIGHTNING_PARTICLE_ID;
    }
}
