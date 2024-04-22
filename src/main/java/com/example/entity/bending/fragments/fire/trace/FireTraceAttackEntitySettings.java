package com.example.entity.bending.fragments.fire.trace;

import com.example.entity.ModEntities;
import com.example.entity.bending.fragments.AttackEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.Map;

public class FireTraceAttackEntitySettings extends AttackEntity {

    // Damage fields

    public boolean explodes = false;
    public int explosionPower = 1;
    public boolean setFire = false;
    public float setFireRadius = 1.0F;
    public int setFireAge = 0;
    public boolean setOnFire = false;
    public int setOnFireTicks = 20;

    public FireTraceAttackEntitySettings(EntityType<? extends FireTraceAttackEntity> fireAttack, World world) {
        super(fireAttack, world);
    }

    // Effect fields


    // Methods


    public FireTraceAttackEntitySettings setExplodes(boolean explodes) {
        this.explodes = explodes;
        return this;
    }

    public FireTraceAttackEntitySettings setExplosionPower(int explosionPower) {
        this.explosionPower = explosionPower;
        return this;
    }

    public FireTraceAttackEntitySettings setSetFire(boolean setFire) {
        this.setFire = setFire;
        return this;
    }

    public FireTraceAttackEntitySettings setSetFireRadius(float setFireRadius) {
        this.setFireRadius = setFireRadius;
        return this;
    }

    public FireTraceAttackEntitySettings setSetOnFire(boolean setOnFire) {
        this.setOnFire = setOnFire;
        return this;
    }

    public FireTraceAttackEntitySettings setSetOnFireTicks(int setOnFireTicks) {
        this.setOnFireTicks = setOnFireTicks;
        return this;
    }

    public FireTraceAttackEntitySettings setSetFireAge(int setFireAge) {
        this.setFireAge = setFireAge;
        return this;
    }

    @Override
    public FireTraceAttackEntitySettings setDamage(float damage) {
        return (FireTraceAttackEntitySettings) super.setDamage(damage);
    }

    @Override
    public FireTraceAttackEntitySettings setDamageRadius(float damageRadius) {
        return (FireTraceAttackEntitySettings) super.setDamageRadius(damageRadius);
    }

    @Override
    public FireTraceAttackEntitySettings setMaxCollisions(int maxCollisions) {
        return (FireTraceAttackEntitySettings) super.setMaxCollisions(maxCollisions);
    }

    @Override
    public FireTraceAttackEntitySettings setLife(int life) {
        return (FireTraceAttackEntitySettings) super.setLife(life);
    }

    @Override
    public FireTraceAttackEntitySettings setSpeed(float speed) {
        return (FireTraceAttackEntitySettings) super.setSpeed(speed);
    }

    @Override
    public FireTraceAttackEntitySettings addStatusEffect(Identifier statusEffect, int duration, int amplifier) {
        return (FireTraceAttackEntitySettings) super.addStatusEffect(statusEffect, duration, amplifier);
    }

    @Override
    public FireTraceAttackEntitySettings setStatusEffects(Map<Identifier, Integer[]> statusEffects) {
        return (FireTraceAttackEntitySettings) super.setStatusEffects(statusEffects);
    }

    @Override
    public String getClassName() {
        return FireTraceAttackEntity.class.getName();
    }

    public static FireTraceAttackEntitySettings getInstance(World world) {
        return new FireTraceAttackEntitySettings(ModEntities.FIRE_TRACE_ATTACK, world);
    }
}
