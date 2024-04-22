package com.example.effect.fire;

import com.example.effect.ModStatusEffect;
import com.example.particle.ModParticles;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.Objects;

public class ExplodeStatusEffect extends ModStatusEffect {

    public ExplodeStatusEffect(StatusEffectCategory category) {
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
            applyExplodeEffect(entity, amplifier);
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    private void applyExplodeEffect(LivingEntity entity, int amplifier) {
        // Explode effect logic
        entity.getWorld().createExplosion(entity, entity.getX(), entity.getY(), entity.getZ(), amplifier, true, World.ExplosionSourceType.MOB);
        spawnParticles(entity, 100, entity.getPos(), amplifier);
        entity.removeStatusEffect(this);
    }

    @Override
    public Identifier getParticle() {
        return ModParticles.FIRE_BALL_PARTICLE_ID;
    }
}
