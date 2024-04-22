package com.example.networking;

import com.example.networking.packet.RequestSyncClientNBTPacket;
import com.example.networking.packet.SyncClientNBTPacket;
import com.example.networking.packet.misc.PlayerJoinPacket;
import com.example.networking.packet.particles.SpawnParticlePacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
public class Server2ClientModMessages extends ModMessagesIdentifiers {
    public static void registerS2CPackets() {
        nbtPackets();
        playerPackets();
        particlePackets();
    }

    public static void playerPackets() {
        ClientPlayNetworking.registerGlobalReceiver(PLAYER_JOIN, PlayerJoinPacket::receive);
    }

    public static void nbtPackets() {
        ClientPlayNetworking.registerGlobalReceiver(SYNC_NBT, SyncClientNBTPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(REQUEST_SYNC_NBT, RequestSyncClientNBTPacket::receive);
    }

    private static void particlePackets() {
        ClientPlayNetworking.registerGlobalReceiver(SPAWN_PARTICLE, SpawnParticlePacket::receive);
    }
}
