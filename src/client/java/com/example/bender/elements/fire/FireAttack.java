package com.example.bender.elements.fire;

import com.example.ElementalBendersMod;
import com.example.bender.Attack;
import com.example.networking.Client2ServerModMessages;
import com.example.networking.ModMessagesIdentifiers;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;

import java.util.Map;

public abstract class FireAttack extends Attack {

    protected static void sendAttackPacket(Map<String, Object> map) {
        PacketByteBuf buf = PacketByteBufs.create();

        buf.writeMap(
                map,
                PacketByteBuf::writeString,
                Attack::writeObject
        );

        ClientPlayNetworking.send(
                ModMessagesIdentifiers.ABILITY,
                buf
        );
    }

    protected static void getShootSound(MinecraftClient client) {

    }
}
