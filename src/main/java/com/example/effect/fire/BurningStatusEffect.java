package com.example.effect.fire;

import com.example.effect.ModStatusEffect;
import com.example.particle.ModParticles;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;

import java.util.Objects;

public class BurningStatusEffect extends ModStatusEffect {


    public BurningStatusEffect(StatusEffectCategory category) {
        super(category, 0x000000);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        super.applyUpdateEffect(entity, amplifier);
        // Spawn particles
        spawnParticles(entity, 2);

        // Get the time the entity has been affected by the status effect
        int duration = Objects.requireNonNull(entity.getStatusEffect(this)).getDuration();
        entity.setOnFire(true);

//        setFireBlock(entity);

        if (duration % 20 == 1) {
            applyBurningEffect(entity, amplifier);
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public Identifier getParticle() {
        return ModParticles.FIRE_PARTICLE_ID;
    }

    private void applyBurningEffect(LivingEntity entity, int amplifier) {
        // Apply burning damage effect to entity
        entity.damage(entity.getDamageSources().onFire(), amplifier);
    }

    private void setFireBlock(LivingEntity entity) {
        // Set block on fire
        BlockState blockState = entity.getWorld().getBlockState(entity.getBlockPos());
        if (blockState.isReplaceable()) {
            entity.getWorld().setBlockState(entity.getBlockPos(), Blocks.FIRE.getDefaultState());
        }
    }
}
