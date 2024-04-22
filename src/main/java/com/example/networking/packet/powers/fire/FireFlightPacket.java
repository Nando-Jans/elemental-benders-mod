package com.example.networking.packet.powers.fire;

import com.example.core.ActivatePowerPacket;
import com.example.util.BendingData;
import com.example.util.EntityDataSaver;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

import java.util.Objects;

public class FireFlightPacket extends ActivatePowerPacket {

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {

        boolean fireFlying = buf.readBoolean();

        if (fireFlying) {
            player.startFallFlying();
        } else {
            player.stopFallFlying();
        }

        BendingData.setFireFlying((EntityDataSaver) player, fireFlying);
    }
}
