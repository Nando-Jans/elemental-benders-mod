package com.example.entity.bending.fragments.fire.trace;

import com.example.entity.ModEntities;
import com.example.networking.send.SendSpawnParticlePacket;
import com.example.particle.ModParticles;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.text.Text;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class FireTraceAttackEntity extends FireTraceAttackEntitySettings {

    public FireTraceAttackEntity(EntityType<? extends FireTraceAttackEntity> entityType, World world) {
        super(entityType, world);
    }

    public FireTraceAttackEntity(World world) {
        super(ModEntities.FIRE_TRACE_ATTACK, world);
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) {
            if (this.explodes) {
                applyExplosion();
            }

            if (this.setOnFire) {
                applySetOnFire();
            }

            if (this.setFire) {
                applySetFire();
            }

            if (this.damageRadius > 0) {
                applyDamageRadius();
            }

            if (this.maxCollisions > 1) {
                this.maxCollisions--;
            } else {
                this.discard();
            }
        }
    }

    private void applyExplosion() {
        boolean bl = this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING);
        this.getWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(), (float)this.explosionPower, bl, World.ExplosionSourceType.MOB);
    }

    private void applySetOnFire() {
        this.getWorld().getEntitiesByClass(LivingEntity.class, this.getBoundingBox().expand(this.setFireRadius), (livingEntity) -> {
            if (livingEntity == this.getOwner()) {
                return true;
            }
            livingEntity.setOnFireFor(this.setOnFireTicks);
            return true;
        });
    }

    protected void applySetFire() {

        BlockPos thisBlockPos = this.getBlockPos();
        float handledRadius = setFireRadius / 2;

        // Calculate the position of the start block
        Vec3d blockPosStartVec = thisBlockPos.toCenterPos().add(-handledRadius - 1, -handledRadius - 1, -handledRadius - 1);
        BlockPos blockPosStart = new BlockPos((int) blockPosStartVec.x, (int) blockPosStartVec.y, (int) blockPosStartVec.z);

        // Calculate the position of the end block
        Vec3d blockPosEndVec = thisBlockPos.toCenterPos().add(handledRadius, handledRadius, handledRadius);
        BlockPos blockPosEnd = new BlockPos((int) blockPosEndVec.x, (int) blockPosEndVec.y, (int) blockPosEndVec.z);

        for (BlockPos blockPos : BlockPos.iterate(blockPosStart, blockPosEnd)) {
            // Test if the block is replaceable
            if (getWorld().getBlockState(blockPos).isAir()) {
                getWorld().setBlockState(blockPos, Blocks.FIRE.getDefaultState().with(FireBlock.AGE, setFireAge));
                getWorld().getPlayers().forEach(player -> {
                    SendSpawnParticlePacket.send(
                            player,
                            blockPos.toCenterPos(),
                            Vec3d.ZERO,
                            0.4f,
                            0,
                            10,
                            ModParticles.FIRE_PARTICLE_ID
                    );
                });
            }
        }
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);

        if (!getWorld().isClient && damageRadius < 1) {
            applyDamageAndStatusEffects(
                    (LivingEntity) entityHitResult.getEntity(),
                    Objects.requireNonNull(this.getOwner()).getDamageSources().onFire()
            );
        }
    }

    @Override
    protected void initDataTracker() {

    }

    // Turn of the friction of the entity
    @Override
    protected float getDragInWater() {
        return 1.0F;
    }

    @Override
    protected float getDrag() {
        return 1.0F;
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Nullable
    @Override
    protected ParticleEffect getParticleType() {
        return null;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.age >= life) {
            this.discard();
        }

        if (!this.getWorld().isClient && shouldSpawnSprintingParticles()) {
            spawnFireBallParticles();
        }
    }

    @Override
    public boolean shouldSpawnSprintingParticles() {
        // When the velocity is greater than 0.1, spawn particles
        if (this.getVelocity().lengthSquared() > 0.01) {
            return true;
        }
        return false;
    }

    protected void spawnFireBallParticles() {
        Vec3d vec3d = this.getVelocity();
        getWorld().getPlayers().forEach(player -> {
            SendSpawnParticlePacket.send(
                    player,
                    this.getPos(),
                    new Vec3d(vec3d.x, vec3d.y, vec3d.z),
                    0.2f,
                    0.2f,
                    5,
                    ModParticles.FIRE_PARTICLE_ID
            );
            SendSpawnParticlePacket.send(
                    player,
                    this.getPos(),
                    new Vec3d(vec3d.x, vec3d.y, vec3d.z),
                    0f,
                    0f,
                    1,
                    ModParticles.FIRE_BALL_PARTICLE_ID
            );
        });
    }

    @Override
    public Text getStyledDisplayName() {
        return super.getStyledDisplayName();
    }
}
