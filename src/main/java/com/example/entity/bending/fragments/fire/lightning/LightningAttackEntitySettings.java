package com.example.entity.bending.fragments.fire.lightning;

import com.example.entity.ModEntities;
import com.example.entity.bending.fragments.AttackEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.Map;

public class LightningAttackEntitySettings extends AttackEntity {

    public boolean chains = false;
    public int chainLength = 0;
    public float chainDamageFalloff = 1F;
    public int chainSplitAmount = 1;
    public float chainRange = 5;



    protected LightningAttackEntitySettings(EntityType<? extends LightningAttackEntity> entityType, World world) {
        super(entityType, world);
    }

    public LightningAttackEntitySettings setChains(boolean chains) {
        this.chains = chains;
        return this;
    }

    public LightningAttackEntitySettings setChainLength(int chainLength) {
        this.chainLength = chainLength;
        return this;
    }

    public LightningAttackEntitySettings setChainDamageFalloff(float chainDamageFalloff) {
        this.chainDamageFalloff = chainDamageFalloff;
        return this;
    }

    public LightningAttackEntitySettings setChainSplitAmount(int chainSplitAmount) {
        this.chainSplitAmount = chainSplitAmount;
        return this;
    }

    public LightningAttackEntitySettings setChainRange(float chainRange) {
        this.chainRange = chainRange;
        return this;
    }

    public LightningAttackEntitySettings setDamage(float damage) {
        this.damage = damage;
        return this;
    }

    public LightningAttackEntitySettings setDamageRadius(float damageRadius) {
        this.damageRadius = damageRadius;
        return this;
    }

    public LightningAttackEntitySettings setMaxCollisions(int maxCollisions) {
        this.maxCollisions = maxCollisions;
        return this;
    }

    public LightningAttackEntitySettings setLife(int life) {
        this.life = life;
        return this;
    }

    public LightningAttackEntitySettings setSpeed(float speed) {
        this.speed = speed;
        return this;
    }

    public LightningAttackEntitySettings addStatusEffect(Identifier statusEffect, int duration, int amplifier) {
        this.statusEffects.put(statusEffect, new Integer[]{duration, amplifier});
        return this;
    }

    public LightningAttackEntitySettings setStatusEffects(Map<Identifier, Integer[]> statusEffects) {
        this.statusEffects = statusEffects;
        return this;
    }


    @Override
    public String getClassName() {
        return LightningAttackEntity.class.getName();
    }

    public static LightningAttackEntitySettings getInstance(World world) {
        return new LightningAttackEntitySettings(ModEntities.LIGHTNING_ATTACK, world);
    }
}
