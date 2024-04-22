package com.example.particle.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import org.jetbrains.annotations.Nullable;

public class FireBallParticle extends SpriteBillboardParticle {
    protected FireBallParticle(ClientWorld level, double x, double y, double z, SpriteProvider spriteSet, double xd, double yd, double zd, float scale) {
        super(level, x, y, z, xd, yd, zd);
        this.velocityMultiplier = 0.98F;
        this.gravityStrength = 0.0F;
        this.scale *= scale;
        this.maxAge = (int) (10.0D / (Math.random() * 0.8D + 0.2D));
        this.setSpriteForAge(spriteSet);

        // Set the color of the fire particle to a random shade between yellow and white
        this.setColor(1.0F, 1.0F , 0.5F);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        super.tick();
        fadeOut();
        fadeToRed();
        scale(0.9F);
    }

    @Override
    protected int getBrightness(float tint) {
        return 15728880;
    }

    protected void fadeOut() {
        this.alpha = (-(1/(float)this.maxAge) * this.age + 1);
    }
    protected void fadeToRed() {
        // This function fades the fire particle to red as it ages
        this.setColor(1.0F, 1.0F - (float)this.age / (float)this.maxAge, 0.5F - (float)this.age / ((float)this.maxAge * 2));
    }
    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {

        private final SpriteProvider spriteSet;

        public Factory(SpriteProvider spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(DefaultParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new FireBallParticle(world, x, y, z, this.spriteSet, velocityX, velocityY, velocityZ, 10.0F);
        }
    }
}
