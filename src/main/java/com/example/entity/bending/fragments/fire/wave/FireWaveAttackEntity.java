package com.example.entity.bending.fragments.fire.wave;

import com.example.entity.ModEntities;
import com.example.entity.bending.fragments.fire.trace.FireTraceAttackEntitySettings;
import com.example.networking.send.SendSpawnParticlePacket;
import com.example.particle.ModParticles;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class FireWaveAttackEntity extends FireWaveAttackEntitySettings {

    public FireWaveAttackEntity(EntityType<? extends FireWaveAttackEntity> entityType, World world) {
        super(entityType, world);
    }

    public FireWaveAttackEntity(World world) {
        super(ModEntities.FIRE_WAVE_ATTACK, world);
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) {

            if (this.maxCollisions > 1) {
                this.maxCollisions--;
            } else {
                this.discard();
            }
        }
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);

        if (!getWorld().isClient && damageRadius < 1) {
            Entity entity = entityHitResult.getEntity();
            Entity entity2 = this.getOwner();

            entity.damage(this.getDamageSources().magic(), damage);
            if (entity2 instanceof LivingEntity) {
                this.applyDamageEffects((LivingEntity) entity2, entity);
            }
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

        if (isWave) {
            applyWave();
        }
    }

    private float waveSize = 3;
    private final float baseWaveSize = 3;

    public Set<BlockPos> getAllSpherePositions(double radius, int numberOfPoints) {
        Set<BlockPos> positions = new HashSet<>();
        BlockPos centerPos = getBlockPos();

        for (int i = 0; i < numberOfPoints; i++) {
            double angle1 = Math.random() * Math.PI * 2;
            double angle2 = Math.random() * Math.PI * 2;

            // Calculate positions outward from the center
            for (double r = baseWaveSize; r <= radius; r += 0.5) { // Increment in smaller steps for precision
                int x = (int) (centerPos.getX() + (r * Math.cos(angle1) * Math.sin(angle2)));
                int y = (int) (centerPos.getY() + (r * Math.cos(angle2)));
                int z = (int) (centerPos.getZ() + (r * Math.sin(angle1) * Math.sin(angle2)));

                BlockPos firePos = new BlockPos(x, y, z);

                // If the position is not air or is a non-replaceable block, stop adding positions in this direction
                if (!getWorld().isAir(firePos) && !getWorld().getBlockState(firePos).isReplaceable()) {
                    break;
                }

                positions.add(firePos); // This position is valid for fire placement
            }
        }

        return positions;
    }

    public Set<BlockPos> getAngledSpherePositions(double radius, int numberOfPoints) {
        Set<BlockPos> positions = new HashSet<>();
        BlockPos centerPos = getBlockPos(); // The starting position (e.g., the player's position)
        double yaw = Math.toRadians(getYaw() + 90); // Adjusting yaw orientation
        double pitch = Math.toRadians(-getPitch()); // Inverting pitch as positive pitch looks down

        // Determine the directions to stop spreading in
        boolean[][][] directionStopped = new boolean[(int) (radius * 2) + 1][(int) (radius * 2) + 1][(int) (radius * 2) + 1];

        for (int i = 0; i < numberOfPoints; i++) {
            double hAngle = Math.random() * Math.toRadians(waveSpread) - Math.toRadians(waveSpread) / 2;
            double vAngle = Math.random() * Math.toRadians(waveSpread) - Math.toRadians(waveSpread) / 2;

            for (double r = baseWaveSize; r <= radius; r += 0.5) { // Increment in smaller steps for precision
                int x = (int) Math.round(centerPos.getX() + (r * Math.cos(yaw + hAngle) * Math.cos(pitch + vAngle)));
                int y = (int) Math.round(centerPos.getY() + (r * Math.sin(pitch + vAngle)));
                int z = (int) Math.round(centerPos.getZ() + (r * Math.sin(yaw + hAngle) * Math.cos(pitch + vAngle)));

                BlockPos pos = new BlockPos(x, y, z);
                int dx = x - centerPos.getX() + (int)radius; // Offset for array indexing
                int dy = y - centerPos.getY() + (int)radius;
                int dz = z - centerPos.getZ() + (int)radius;

                if (directionStopped[dx][dy][dz]) {
                    break; // Stop spreading in this direction
                }

                if (!getWorld().isAir(pos) && !getWorld().getBlockState(pos).isReplaceable()) {
                    directionStopped[dx][dy][dz] = true; // Mark direction as stopped
                    break;
                }

                positions.add(pos); // Add the position for a fire block
            }
        }

        return positions;
    }

    private void applyWave() {
        Set<BlockPos> blocks;

        if (waveSpread <= 359.0F) {
            // Calculate the volume of the cone, ensuring a minimum value
            double rawConeVolume = (1.0D/3.0D) * Math.PI * Math.pow(waveSize, 2) * waveSize;
            int coneVolume = Math.max((int) Math.ceil(rawConeVolume * 0.1), 10); // Apply density factor and ensure a minimum

            // Get all the positions of the sphere in an angled way
            blocks = getAngledSpherePositions(waveSize, coneVolume);
        } else {
            // Calculate how many blocks are on the area of the sphere is based on the wave size
            double rawSphereArea = 4 * Math.PI * Math.pow(waveSize, 2);
            int sphereArea = Math.max((int) Math.ceil(rawSphereArea), 10); // Apply density factor and ensure a minimum

            // Get all the positions of the sphere
            blocks = getAllSpherePositions(waveSize, sphereArea);
        }

        // Go over every block on the area of the sphere and set it on fire
        for (BlockPos blockPos : blocks) {
            // Test if the block is replaceable
            getWorld().setBlockState(blockPos, Blocks.FIRE.getDefaultState().with(FireBlock.AGE, setFireAge));

            if (waveSpread <= 359.0F) {
                addParticleEffect(blockPos.toCenterPos(), getRotationVector(), ModParticles.FIRE_PARTICLE_ID);
            } else {
                addParticleEffect(blockPos.toCenterPos(), new Vec3d(0, 0, 0), ModParticles.FIRE_PARTICLE_ID);
            }

            if (setOnFire) {
                applySetOnFire(blockPos);
            }
        }

        waveSize += waveSpeed;
    }

    private void applySetOnFire(BlockPos pos) {

        // Set the entities at the position on fire
        getWorld().getEntitiesByClass(LivingEntity.class, new Box(pos), entity -> true).forEach(entity -> {
            entity.setOnFireFor(setOnFireTicks);
        });
    }

    public void addParticleEffect(Vec3d pos, Vec3d velocity, Identifier particleType) {
        // Get every player that is within the radius of the fire attack and therefore should see the particles
        getWorld().getPlayers().forEach(player -> {
            SendSpawnParticlePacket.send(
                    player,
                    pos,
                    velocity,
                    0.1f,
                    0.1f,
                    1,
                    particleType
            );
        });
    }

    @Override
    public Text getStyledDisplayName() {
        return super.getStyledDisplayName();
    }
}
