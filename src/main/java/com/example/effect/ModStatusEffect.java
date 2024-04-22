package com.example.effect;

import com.example.networking.send.SendSpawnParticlePacket;
import com.example.particle.ModParticles;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

abstract public class ModStatusEffect extends StatusEffect {
    protected ModStatusEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    abstract public Identifier getParticle();

    protected void spawnParticles(LivingEntity entity, int amount) {
        spawnParticles(entity, amount, entity.getPos(), 1F);
    }

    protected void spawnParticles(LivingEntity entity, int amount, Vec3d pos) {
        spawnParticles(entity, amount, pos, 1F);
    }

    protected void spawnParticles(LivingEntity entity, int amount, Vec3d pos, float posRandomness) {
        if (entity.getWorld().isClient()) return;
        // Spawn particles
        entity.getWorld().getPlayers().forEach(player -> {
            // Send packet to spawn particles
            SendSpawnParticlePacket.send(
                    player,
                    pos,
                    Vec3d.ZERO,
                    posRandomness,
                    1F,
                    amount,
                    getParticle()
            );
        });
    }

    protected List<BlockPos> getBlocksInRadius(BlockPos pos, int radius) {
        // Get the blocks in the radius around the entity
        List<BlockPos> blocks = new ArrayList<>();
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    blocks.add(new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z));
                }
            }
        }
        return blocks;
    }
}
