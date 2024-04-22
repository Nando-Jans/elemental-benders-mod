package com.example.event;

import com.example.ElementalBendersMod;
import com.example.networking.Server2ClientModMessages;
import com.example.util.BendingData;
import com.example.util.EntityDataSaver;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;

public class ClientPlayerJoinEvent {
    public static void registerEvent() {
        // Register the event
        ClientTickEvents.END_CLIENT_TICK.register(ClientPlayerJoinEvent::onClientJoin);
    }

    private static boolean eventFired = false;

    private static void onClientJoin(MinecraftClient client) {
        if (!eventFired && client.player != null) {
            // The player has joined the game
            ElementalBendersMod.LOGGER.info("The persistent data of the player on the client side is: " + BendingData.get((EntityDataSaver) client.player).toString());

            ClientPlayNetworking.send(Server2ClientModMessages.REQUEST_SYNC_NBT, PacketByteBufs.create());

            eventFired = true;
        }
    }
}
