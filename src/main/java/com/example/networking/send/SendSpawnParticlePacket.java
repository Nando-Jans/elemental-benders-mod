package com.example.networking.send;

import com.example.networking.ModMessagesIdentifiers;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class SendSpawnParticlePacket {
    public static void send(PlayerEntity player, Vec3d pos, Vec3d velocity, float posRandomness, float velocityRandomness, int amount, Identifier particleId) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeVec3d(pos);
        buf.writeVec3d(velocity);
        buf.writeFloat(posRandomness);
        buf.writeFloat(velocityRandomness);
        buf.writeInt(amount);
        buf.writeIdentifier(particleId);
        ServerPlayNetworking.send((ServerPlayerEntity) player, ModMessagesIdentifiers.SPAWN_PARTICLE, buf);
    }
}
