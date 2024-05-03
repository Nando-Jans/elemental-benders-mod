package com.example.entity.mob.bender.fire;

import com.example.ElementalBendersMod;
import com.example.entity.bending.fragments.fire.trace.FireTraceAttackEntity;
import com.example.entity.bending.fragments.fire.trace.FireTraceAttackEntitySettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.util.EnumSet;
import java.util.UUID;

public class FireBenderEntity extends PathAwareEntity implements Angerable {
    @Nullable
    private UUID angryAt;
    private int angerTime;

    // TODO: Figure out why this entity is slow

    public FireBenderEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(4, new FireBenderAttackGoal(this));
        this.goalSelector.add(5, new SwimGoal(this));
        this.goalSelector.add(6, new GoToWalkTargetGoal(this, 1.0D));
        this.goalSelector.add(7, new WanderAroundGoal(this, 1.0D));
        this.goalSelector.add(8, new StopAndLookAtEntityGoal(this, PlayerEntity.class, 5.0F));
        this.goalSelector.add(9, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder fireBenderAttributeContainer() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0D)
                .add(EntityAttributes.GENERIC_ARMOR, 2.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.0D)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 0.0D)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 4.0D);
    }

    @Override
    public int getAngerTime() {
        return this.angerTime;
    }

    @Override
    public void setAngerTime(int angerTime) {
        this.angerTime = angerTime;
    }

    @Nullable
    @Override
    public UUID getAngryAt() {
        return this.angryAt;
    }

    @Override
    public void setAngryAt(@Nullable UUID angryAt) {
        this.angryAt = angryAt;
    }

    @Override
    public void chooseRandomAngerTime() {
        this.angerTime = (int) (Math.random() * 2000);
    }



    @Override
    public void tick() {
        super.tick();

        if (this.angryAt != null) {
            ElementalBendersMod.LOGGER.info("Is angry at: " + this.angryAt);
        }
    }

    private static class FireBenderAttackGoal extends Goal {
        private final FireBenderEntity fireBender;
        private int fireballsFired;
        private int fireballCooldown;
        private int targetNotVisibleTicks;

        public FireBenderAttackGoal(FireBenderEntity fireBender) {
            this.fireBender = fireBender;
            this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
        }

        public boolean canStart() {
            LivingEntity livingEntity = this.fireBender.getTarget();
            return livingEntity != null && livingEntity.isAlive() && this.fireBender.canTarget(livingEntity);
        }

        public void start() {
            this.fireballsFired = 0;
        }

        public void stop() {
            this.targetNotVisibleTicks = 0;
        }

        public boolean shouldRunEveryTick() {
            return true;
        }

        public void tick() {
            --this.fireballCooldown;
            LivingEntity livingEntity = this.fireBender.getTarget();
            if (livingEntity != null) {
                boolean bl = this.fireBender.getVisibilityCache().canSee(livingEntity);
                if (bl) {
                    this.targetNotVisibleTicks = 0;
                } else {
                    ++this.targetNotVisibleTicks;
                }

                double d = this.fireBender.squaredDistanceTo(livingEntity);
                if (d < 4.0D) {
                    if (!bl) {
                        return;
                    }

                    if (this.fireballCooldown <= 0) {
                        this.fireballCooldown = 20;
                        this.fireBender.tryAttack(livingEntity);
                    }

                    this.fireBender.getMoveControl().moveTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 1.0D);
                } else if (d < this.getFollowRange() * this.getFollowRange() && bl) {
                    double e = livingEntity.getX() - this.fireBender.getX();
                    double f = livingEntity.getBodyY(0.5D) - this.fireBender.getBodyY(0.5D);
                    double g = livingEntity.getZ() - this.fireBender.getZ();

                    this.fireAttackLogic(e, f, g);

                    this.fireBender.getLookControl().lookAt(livingEntity, 10.0F, 10.0F);
                } else if (this.targetNotVisibleTicks < 5) {
                    this.fireBender.getMoveControl().moveTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 1.0D);
                }

                super.tick();
            }
        }

        private double getFollowRange() {
            return this.fireBender.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE);
        }

        private void fireAttackLogic(double e, double f, double g) {
            FireTraceAttackEntitySettings settings = FireTraceAttackEntitySettings.getInstance(this.fireBender.getWorld())
                    .setDamage(3.0F)
                    .setDamageRadius(1.0F)
                    .setExplodes(true)
                    .setExplosionPower(3)
                    .setLife(100)
                    .setSpeed(2.0F);

            FireTraceAttackEntity fireTraceAttackEntity;

            try {
                fireTraceAttackEntity = (FireTraceAttackEntity) FireTraceAttackEntity.fromMap(settings.toMap(), this.fireBender.getWorld());
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException  e1) {
                return;
            }

            fireTraceAttackEntity.setOwner(this.fireBender);

            // Raycast the firetraceattack entity from the firebender to the target
            fireTraceAttackEntity.setVelocity(
                    new Vec3d(e, f, g).normalize().multiply(2.0F)
            );

            fireTraceAttackEntity.setYaw(this.fireBender.getYaw());
            fireTraceAttackEntity.setPitch(this.fireBender.getPitch());

            fireTraceAttackEntity.setPosition(this.fireBender.getPos().add(0, this.fireBender.getEyeHeight(this.fireBender.getPose()), 0));

            this.fireBender.getWorld().spawnEntity(fireTraceAttackEntity);
        }
    }
}
