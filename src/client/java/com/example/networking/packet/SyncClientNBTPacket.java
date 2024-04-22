package com.example.networking.packet;

import com.example.util.BendingData;
import com.example.util.EntityDataSaver;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

import java.util.Objects;

public class SyncClientNBTPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        EntityDataSaver entityDataSaver = (EntityDataSaver) client.player;
        BendingData.set(entityDataSaver, Objects.requireNonNull(buf.readNbt()));
    }
}
