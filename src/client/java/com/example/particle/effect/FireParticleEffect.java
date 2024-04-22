package com.example.particle.effect;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.*;

public class FireParticleEffect implements ParticleEffect {
    private final float temperature;
    private final float speed;
    private final float size;
    private final float brightness;
    private final float smoke;

    public FireParticleEffect(float temperature, float speed, float size, float brightness, float smoke) {
        this.temperature = temperature;
        this.speed = speed;
        this.size = size;
        this.brightness = brightness;
        this.smoke = smoke;
    }

    @Override
    public ParticleType<?> getType() {
        if (1.0F < temperature) {
            return ParticleTypes.SOUL_FIRE_FLAME;
        } else if (0.5F < temperature && temperature < 1.0F) {
            return ParticleTypes.FLAME;
        } else {
            return ParticleTypes.SMOKE;
        }
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeFloat(temperature);
        buf.writeFloat(speed);
        buf.writeFloat(size);
        buf.writeFloat(brightness);
        buf.writeFloat(smoke);
    }

    @Override
    public String asString() {
        return null;
    }
}
