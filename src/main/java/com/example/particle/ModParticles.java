package com.example.particle;

import com.example.ElementalBendersMod;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModParticles {
    // Particle types
    public static final DefaultParticleType FIRE_PARTICLE = FabricParticleTypes.simple();
    public static final DefaultParticleType FIRE_BALL_PARTICLE = FabricParticleTypes.simple();
    public static final DefaultParticleType LIGHTNING_PARTICLE = FabricParticleTypes.simple();

    // Particle id's
    public static final Identifier FIRE_PARTICLE_ID = new Identifier(ElementalBendersMod.MOD_ID, "fire_particle");
    public static final Identifier FIRE_BALL_PARTICLE_ID = new Identifier(ElementalBendersMod.MOD_ID, "fire_ball_particle");
    public static final Identifier LIGHTNING_PARTICLE_ID = new Identifier(ElementalBendersMod.MOD_ID, "lightning_particle");

    public static void register() {
        Registry.register(Registries.PARTICLE_TYPE, FIRE_PARTICLE_ID, FIRE_PARTICLE);
        Registry.register(Registries.PARTICLE_TYPE, FIRE_BALL_PARTICLE_ID, FIRE_BALL_PARTICLE);
        Registry.register(Registries.PARTICLE_TYPE, LIGHTNING_PARTICLE_ID, LIGHTNING_PARTICLE);
    }
}
