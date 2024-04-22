package com.example.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;

public class ServerTickHandler implements ServerTickEvents.StartTick {
    public static void registerServerTick() {
        ServerTickEvents.START_SERVER_TICK.register(new ServerTickHandler());
    }

    @Override
    public void onStartTick(MinecraftServer server) {

    }
}
