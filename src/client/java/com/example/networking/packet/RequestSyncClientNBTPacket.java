package com.example.networking.packet;

import com.example.core.Packet;
import com.example.networking.Server2ClientModMessages;
import com.example.util.BendingData;
import com.example.util.EntityDataSaver;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

import java.util.Objects;

public class RequestSyncClientNBTPacket extends Packet {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        ClientPlayNetworking.send(Server2ClientModMessages.SYNC_NBT, PacketByteBufs.create().writeNbt(BendingData.get((EntityDataSaver) client.player)));
    }
}
