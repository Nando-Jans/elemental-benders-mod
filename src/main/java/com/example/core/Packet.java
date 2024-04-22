package com.example.core;

import net.fabricmc.fabric.api.lookup.v1.block.BlockApiCache;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class Packet {
    public static final Logger LOGGER = LoggerFactory.getLogger("packets");

    public static ServerWorld getServerWorld(MinecraftServer server, ServerPlayerEntity player) {
        return Objects.requireNonNull(server.getWorld(player.getWorld().getRegistryKey()));
    }
}
