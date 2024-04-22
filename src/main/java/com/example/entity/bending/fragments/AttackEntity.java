package com.example.entity.bending.fragments;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class AttackEntity extends ExplosiveProjectileEntity {
    public float damage = 1.0F;
    public float damageRadius = 1.0F;
    public int maxCollisions = 1;
    public int life = 100;
    public float speed = 1.0F;

    public Map<Identifier, Integer[]> statusEffects = new HashMap<>();

    protected AttackEntity(EntityType<? extends ExplosiveProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public AttackEntity setDamage(float damage) {
        this.damage = damage;
        return this;
    }

    public AttackEntity setDamageRadius(float damageRadius) {
        this.damageRadius = damageRadius;
        return this;
    }

    public AttackEntity setMaxCollisions(int maxCollisions) {
        this.maxCollisions = maxCollisions;
        return this;
    }

    public AttackEntity setLife(int life) {
        this.life = life;
        return this;
    }

    public AttackEntity setSpeed(float speed) {
        this.speed = speed;
        return this;
    }

    public AttackEntity addStatusEffect(Identifier statusEffect, int duration, int amplifier) {
        this.statusEffects.put(statusEffect, new Integer[]{duration, amplifier});
        return this;
    }

    public AttackEntity setStatusEffects(Map<Identifier, Integer[]> statusEffects) {
        this.statusEffects = statusEffects;
        return this;
    }

    abstract public String getClassName();

    public Map<String, Object> toMap() {
        // Get the map of all properties
        Map<String, Object> map = new HashMap<>();
        map.put("className", getClassName());

        // Iterate over all fields and put them in the map
        for (Field field : this.getClass().getDeclaredFields()) {
            try {
                map.put(field.getName(), field.get(this));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        // Iterate over all the fields of AttackEntity and put them in the map
        for (Field field : AttackEntity.class.getDeclaredFields()) {
            try {
                map.put(field.getName(), field.get(this));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }


        return map;
    }

    public static AttackEntity fromMap(Map<String, Object> map, World world) throws
            ClassNotFoundException,
            NoSuchMethodException,
            InvocationTargetException,
            InstantiationException,
            IllegalAccessException {

        String className = (String) map.get("className");

        AttackEntity attack = (AttackEntity) Class.forName(className).getConstructor(World.class).newInstance(world);

        for (Field field : Class.forName(className).getFields()) {
            Object value = map.get(field.getName());

            if (value != null) {
                try {
                    field.set(attack, value);
                } catch (IllegalAccessException | IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }

        for (Field field : AttackEntity.class.getFields()) {
            Object value = map.get(field.getName());
            if (value != null) {
                try {
                    field.set(attack, value);
                } catch (IllegalAccessException | IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }

        return attack;
    }

    protected float getDamage() {
        return damage;
    }

    protected void applyDamageAndStatusEffects(LivingEntity entity, DamageSource damageSource) {
        // Apply damage to entities in the damage radius
        entity.damage(damageSource, getDamage());
        // Apply status effects to entities in the damage radius
        for (Map.Entry<Identifier, Integer[]> entry : statusEffects.entrySet()) {
            StatusEffectInstance statusEffectInstance = new StatusEffectInstance(
                    Objects.requireNonNull(Registries.STATUS_EFFECT.get(entry.getKey())),
                    entry.getValue()[0],
                    entry.getValue()[1]
            );
            entity.addStatusEffect(statusEffectInstance);
        }
    }

    protected void applyDamageRadius() {
        Entity owner = this.getOwner();
        // Apply damage effects
        this.getWorld().getEntitiesByClass(LivingEntity.class, this.getBoundingBox().expand(damageRadius), (entity) -> {
            if (entity != owner) {
                // Apply damage falloff based on distance
                double distance = this.getPos().distanceTo(entity.getPos());
                float damage = (float) (getDamage() * (1 - distance / damageRadius));
                if (owner == null) return true;
                entity.damage(owner.getDamageSources().magic(), damage);
                this.applyDamageEffects(entity, owner);

                for (Map.Entry<Identifier, Integer[]> entry : statusEffects.entrySet()) {
                    // Apply status effects with amplifier falloff based on distance
                    int amplifier = entry.getValue()[1];
                    int newAmplifier = (int) (amplifier * (1 - distance / damageRadius));

                    StatusEffectInstance statusEffectInstance = new StatusEffectInstance(
                            Objects.requireNonNull(Registries.STATUS_EFFECT.get(entry.getKey())),
                            entry.getValue()[0],
                            newAmplifier
                    );
                    entity.addStatusEffect(statusEffectInstance);
                }
            }
            return true;
        });
    }
}
