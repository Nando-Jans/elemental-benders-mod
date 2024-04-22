package com.example.networking.packet;

import com.example.core.Packet;
import com.example.networking.Client2ServerModMessages;
import com.example.util.BendingData;
import com.example.util.EntityDataSaver;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Objects;

public class RequestSyncServerNBTPacket extends Packet {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        ServerPlayNetworking.send(player, Client2ServerModMessages.SYNC_NBT, PacketByteBufs.create().writeNbt(BendingData.get((EntityDataSaver) player)));
    }
}
