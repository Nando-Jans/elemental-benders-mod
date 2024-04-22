package com.example.effect.fire;

import com.example.effect.ModStatusEffect;
import com.example.particle.ModParticles;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NapalmExplodeStatusEffect extends ModStatusEffect {

    public NapalmExplodeStatusEffect(StatusEffectCategory category) {
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
            applyNapalmExplodeEffect(entity, amplifier);
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    private void applyNapalmExplodeEffect(LivingEntity entity, int amplifier) {
        // Set blocks and entities on fire in a radius around the entity
        // Get the blocks in the radius around the entity
        List<BlockPos> blocks = getBlocksInRadius(entity.getBlockPos(), amplifier);
        // Set the blocks on fire
        blocks.forEach(blockPos -> {
                if (!entity.getEntityWorld().getBlockState(blockPos).isReplaceable()) return;
                entity.getEntityWorld().setBlockState(blockPos, Blocks.FIRE.getDefaultState());
                spawnParticles(entity, 2, blockPos.toCenterPos());
        });
        // Set the entities on fire
        entity.getEntityWorld().getEntitiesByClass(
                LivingEntity.class,
                entity.getBoundingBox()
                        .expand(amplifier, amplifier, amplifier), entity1 -> true)
                        .forEach(entity1 -> entity1.setOnFireFor(5)
        );
        entity.removeStatusEffect(this);
    }

    @Override
    public Identifier getParticle() {
        return ModParticles.FIRE_PARTICLE_ID;
    }
}
