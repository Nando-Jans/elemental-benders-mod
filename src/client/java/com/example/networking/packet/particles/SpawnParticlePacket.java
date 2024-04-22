package com.example.networking.packet.particles;

import com.example.particle.ModParticles;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class SpawnParticlePacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {

        Vec3d pos = buf.readVec3d();
        Vec3d velocity = buf.readVec3d();
        float posRandomness = buf.readFloat();
        float velocityRandomness = buf.readFloat();
        int amount = buf.readInt();
        Identifier particleId = buf.readIdentifier();

        // Get the particle from the registry
        DefaultParticleType particle = (DefaultParticleType) Registries.PARTICLE_TYPE.get(particleId);

        ClientWorld world = client.world;

        if (world == null || particle == null) {
            return;
        }

        // Spawn fire particles at the given position with the given velocity with a random offset of 0.1 5 times
        for (int i = 0; i < amount; i++) {

            Vec3d posOffset = new Vec3d(
                    (Math.random() - 0.5) * posRandomness,
                    (Math.random() - 0.5) * posRandomness,
                    (Math.random() - 0.5) * posRandomness
            );

            Vec3d velocityOffset = new Vec3d(
                    (Math.random() - 0.5) * velocityRandomness,
                    (Math.random() - 0.5) * velocityRandomness,
                    (Math.random() - 0.5) * velocityRandomness
            );

            world.addParticle(
                    particle,
                    pos.x + posOffset.x,
                    pos.y + posOffset.y,
                    pos.z + posOffset.z,
                    velocity.x + velocityOffset.x,
                    velocity.y + velocityOffset.y,
                    velocity.z + velocityOffset.z
            );
        }
    }
}
