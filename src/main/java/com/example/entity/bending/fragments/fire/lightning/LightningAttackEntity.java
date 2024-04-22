package com.example.entity.bending.fragments.fire.lightning;

import com.example.ElementalBendersMod;
import com.example.entity.ModEntities;
import com.example.networking.send.SendSpawnParticlePacket;
import com.example.particle.ModParticles;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class LightningAttackEntity extends LightningAttackEntitySettings {

    public Entity lastTarget = null;

    public LightningAttackEntity(EntityType<? extends LightningAttackEntity> entityType, World world) {
        super(entityType, world);
    }

    public LightningAttackEntity(World world) {
        super(ModEntities.LIGHTNING_ATTACK, world);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.age >= life) {
            ElementalBendersMod.LOGGER.info("Life ended");
            this.discard();
        }

        if (!getWorld().isClient()) {
            spawnLightningParticles();
        }
    }

    public void setLastTarget(Entity lastTarget) {
        this.lastTarget = lastTarget;
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

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
    }

    @Override
    protected void onBlockCollision(BlockState state) {
        // If the block is not passable, discard the entity
        if (!state.isReplaceable()) {
//            this.discard();
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity target = entityHitResult.getEntity();
        if (target == this.getOwner() || target == this || target.getClass() == this.getClass() || target == lastTarget) {
            return;
        }

        applyDamageAndStatusEffects(
                (LivingEntity) target,
                Objects.requireNonNull(getOwner()).getDamageSources().lightningBolt()
        );

        if (chains && chainLength > 0) {
            lastTarget = target;
            // Get the next targets for the chain
            List<Entity> nextTargets = findNextTargets();

            for (Entity nextTarget : nextTargets) {
                spawnNextBolt(nextTarget);
            }
        }
        this.maxCollisions--;
        if (this.maxCollisions <= 0) {
            this.discard();
        }
    }

    @Nullable
    @Override
    protected ParticleEffect getParticleType() {
        return null;
    }

    protected List<Entity> findNextTargets() {
        // This method is called when the lightning bolt should split into new bolts based on the chainSplitAmount
        // Implement the logic to find the next targets and filter only the nearest ones

        List<Entity> entities = getWorld().getEntitiesByClass(
                Entity.class,
                new Box(getPos(), getPos()).expand(chainRange),
                entity ->
                        entity != this
                        && entity != getOwner()
                        && entity.getClass() != LightningAttackEntity.class
                        && entity != lastTarget
        );
        entities.sort(Comparator.comparingDouble(entity -> entity.getPos().distanceTo(this.getPos())));
        return entities.subList(0, Math.min(entities.size(), chainSplitAmount));
    }

    @Override
    protected float getDamage() {
        // Here, apply the effects of the lightning to the target
        return damage * chainDamageFalloff;
    }

    protected void spawnNextBolt(Entity target) {
        // This method is called when the lightning bolt should split
        // into another bolt
        // Implement the logic to spawn the next bolt
        if (target != null) {
            LightningAttackEntity nextBolt = new LightningAttackEntity(getWorld());
            Vec3d pos = this.getPos();
            nextBolt.setPos(pos.x, pos.y, pos.z);
            nextBolt.setChainLength(chainLength-1);
            nextBolt.setChainSplitAmount(chainSplitAmount);
            nextBolt.setChainDamageFalloff(chainDamageFalloff * chainDamageFalloff);
            nextBolt.setChains(chains);
            nextBolt.setChainRange(chainRange);
            nextBolt.setDamage(damage);
            nextBolt.setLife(life);
            nextBolt.setMaxCollisions(maxCollisions);
            nextBolt.setSpeed(speed);
            nextBolt.setOwner(this.getOwner());
            nextBolt.setStatusEffects(statusEffects);
            nextBolt.rotateTowards(target.getPos().add(0, target.getHeight() / 2, 0));
            nextBolt.setLastTarget(lastTarget);
            getWorld().spawnEntity(nextBolt);
        }
    }

    protected void rotateTowards(Vec3d target) {
        Vec3d direction = target.subtract(this.getPos()).normalize();
        this.setVelocity(direction.multiply(speed));
    }

    protected void spawnLightningParticles() {
        Vec3d vec3d = this.getVelocity();
        getWorld().getPlayers().forEach(player -> {
            SendSpawnParticlePacket.send(
                    player,
                    this.getPos(),
                    new Vec3d(vec3d.x, vec3d.y, vec3d.z),
                    0.2f,
                    1f,
                    5,
                    ModParticles.LIGHTNING_PARTICLE_ID
            );
        });
    }
}
