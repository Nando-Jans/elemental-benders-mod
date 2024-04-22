package com.example.event;

import com.example.ElementalBendersMod;
import com.example.networking.Client2ServerModMessages;
import com.example.util.BendingData;
import com.example.util.EntityDataSaver;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class ServerPlayerJoinEvent {
    public static void registerEvent() {
        ServerPlayConnectionEvents.JOIN.register(ServerPlayerJoinEvent::onServerJoin);
    }

    private static void onServerJoin(ServerPlayNetworkHandler handler, PacketSender packetSender, MinecraftServer server) {
        ServerPlayerEntity player = handler.player;

        // Log the player's persistent data on the server side
        ElementalBendersMod.LOGGER.info("The persistent data of the player on the server side is: " + BendingData.get((EntityDataSaver) player).toString());

        // Send the bending type selection screen if the player has not selected a bending type
        if (BendingData.getBendingType((EntityDataSaver) player).isEmpty()) {
            ElementalBendersMod.LOGGER.info("Sending bending type selection screen to player: " + player.getName());
            ServerPlayNetworking.send(player, Client2ServerModMessages.PLAYER_JOIN, PacketByteBufs.create());
        }
    }
}
