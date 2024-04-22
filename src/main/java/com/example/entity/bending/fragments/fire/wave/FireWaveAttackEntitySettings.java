package com.example.entity.bending.fragments.fire.wave;

import com.example.entity.ModEntities;
import com.example.entity.bending.fragments.AttackEntity;
import com.example.entity.bending.fragments.fire.trace.FireTraceAttackEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.Map;

public class FireWaveAttackEntitySettings extends AttackEntity {

    // Damage fields

    public boolean setFire = false;
    public int setFireAge = 15;
    public boolean setOnFire = false;
    public int setOnFireTicks = 20;
    public boolean isStill = false;
    public boolean isWave = false;
    public float waveSpeed = 1.0F;
    public float waveSpread = 360.0F;

    public FireWaveAttackEntitySettings(EntityType<? extends FireWaveAttackEntity> fireAttack, World world) {
        super(fireAttack, world);
    }

    // Effect fields


    // Methods

    public FireWaveAttackEntitySettings setSetFire(boolean setFire) {
        this.setFire = setFire;
        return this;
    }

    public FireWaveAttackEntitySettings setSetOnFire(boolean setOnFire) {
        this.setOnFire = setOnFire;
        return this;
    }

    public FireWaveAttackEntitySettings setSetOnFireTicks(int setOnFireTicks) {
        this.setOnFireTicks = setOnFireTicks;
        return this;
    }

    public FireWaveAttackEntitySettings setSetFireAge(int setFireAge) {
        this.setFireAge = setFireAge;
        return this;
    }

    public FireWaveAttackEntitySettings setIsStill(boolean isStill) {
        this.isStill = isStill;
        return this;
    }

    public FireWaveAttackEntitySettings setIsWave(boolean isWave) {
        this.isWave = isWave;
        return this;
    }

    public FireWaveAttackEntitySettings setWaveSpeed(float waveSpeed) {
        this.waveSpeed = waveSpeed;
        return this;
    }

    public FireWaveAttackEntitySettings setWaveSpread(float waveSpread) {
        this.waveSpread = waveSpread;
        return this;
    }

    @Override
    public FireWaveAttackEntitySettings setDamage(float damage) {
        return (FireWaveAttackEntitySettings) super.setDamage(damage);
    }

    @Override
    public FireWaveAttackEntitySettings setDamageRadius(float damageRadius) {
        return (FireWaveAttackEntitySettings) super.setDamageRadius(damageRadius);
    }

    @Override
    public FireWaveAttackEntitySettings setMaxCollisions(int maxCollisions) {
        return (FireWaveAttackEntitySettings) super.setMaxCollisions(maxCollisions);
    }

    @Override
    public FireWaveAttackEntitySettings setLife(int life) {
        return (FireWaveAttackEntitySettings) super.setLife(life);
    }

    @Override
    public FireWaveAttackEntitySettings setSpeed(float speed) {
        return (FireWaveAttackEntitySettings) super.setSpeed(speed);
    }

    @Override
    public FireWaveAttackEntitySettings addStatusEffect(Identifier statusEffect, int duration, int amplifier) {
        return (FireWaveAttackEntitySettings) super.addStatusEffect(statusEffect, duration, amplifier);
    }

    @Override
    public FireWaveAttackEntitySettings setStatusEffects(Map<Identifier, Integer[]> statusEffects) {
        this.statusEffects = statusEffects;
        return this;
    }

    public static FireWaveAttackEntitySettings getInstance(World world) {
        return new FireWaveAttackEntitySettings(ModEntities.FIRE_WAVE_ATTACK, world);
    }

    @Override
    public String getClassName() {
        return FireWaveAttackEntity.class.getName();
    }
}
