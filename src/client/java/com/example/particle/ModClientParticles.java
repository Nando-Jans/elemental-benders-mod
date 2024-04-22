package com.example.particle;

import com.example.particle.custom.FireBallParticle;
import com.example.particle.custom.FireParticle;
import com.example.particle.custom.LightningParticle;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

public class ModClientParticles {
    public static void register() {
        ParticleFactoryRegistry.getInstance().register(ModParticles.FIRE_PARTICLE, FireParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.FIRE_BALL_PARTICLE, FireBallParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.LIGHTNING_PARTICLE, LightningParticle.Factory::new);
    }
}
