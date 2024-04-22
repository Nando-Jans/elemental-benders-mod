package com.example.networking;

import com.example.networking.packet.RequestSyncServerNBTPacket;
import com.example.networking.packet.SyncServerNBTPacket;
import com.example.networking.packet.powers.fire.AbilityPacket;
import com.example.networking.packet.powers.fire.FireAttackPacket;
import com.example.networking.packet.powers.fire.FireFlightPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class Client2ServerModMessages extends ModMessagesIdentifiers {

    public static void registerC2SPackets() {
        nbtPackets();
        firePackets();
        attackPackets();
    }

    private static void firePackets() {
        fireAttackPackets();
    }

    private static void nbtPackets() {
        ServerPlayNetworking.registerGlobalReceiver(SYNC_NBT, SyncServerNBTPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(REQUEST_SYNC_NBT, RequestSyncServerNBTPacket::receive);
    }

    private static void fireAttackPackets() {
        ServerPlayNetworking.registerGlobalReceiver(FIRE_ATTACK, FireAttackPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(FIRE_FLIGHT_ABILITY, FireFlightPacket::receive);
    }

    private static void attackPackets() {
        ServerPlayNetworking.registerGlobalReceiver(ABILITY, AbilityPacket::receive);
    }
}
