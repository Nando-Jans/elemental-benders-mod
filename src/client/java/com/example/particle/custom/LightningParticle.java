package com.example.particle.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import org.jetbrains.annotations.Nullable;

public class LightningParticle extends SpriteBillboardParticle {

    private final float redColor;
    private final float greenColor;

    protected LightningParticle(ClientWorld level, double x, double y, double z, SpriteProvider spriteSet, double xd, double yd, double zd, float scale) {
        super(level, x, y, z, xd, yd, zd);
        this.velocityMultiplier = 0.98F;
        this.gravityStrength = 0F;
        this.scale *= scale;
        this.maxAge = (int) (5.0D / (Math.random() * 0.8D + 0.2D));
        this.setSpriteForAge(spriteSet);

        this.redColor = 0.6F;
        this.greenColor = 0.8F;

        // Set the color of the fire particle to a random shade between yellow and white
        this.setColor(redColor, greenColor, 1.0F);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        super.tick();
        fadeOut();
        fadeToLightBlue();
        scale(0.99F);
        flicker();
    }


    private void flicker() {
        // After the half of this particles age, it will flicker
        if (this.age > this.maxAge / 2) {
            this.alpha = (float) Math.random() > 0.5F ? 0.0F : 1.0F;
        }
    }

    @Override
    protected int getBrightness(float tint) {
        return 15728880;
    }

    protected void fadeOut() {
        this.alpha = (-(1/(float)this.maxAge) * this.age + 1);
    }
    protected void fadeToLightBlue() {
        // This function fades the lightning particle to light blue as it ages
        float fadeRed = redColor - (float)age / (float)maxAge / (4 * redColor);
        float fadeGreen = greenColor - (float)age / (float)maxAge / (4 * greenColor);
        setColor(fadeRed, fadeGreen, 1.0F);
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
            return new LightningParticle(world, x, y, z, this.spriteSet, velocityX, velocityY, velocityZ, 2.0F);
        }
    }
}
