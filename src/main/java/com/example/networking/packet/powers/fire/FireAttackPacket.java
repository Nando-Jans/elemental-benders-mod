package com.example.networking.packet.powers.fire;

import com.example.core.ActivatePowerPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Map;

public class FireAttackPacket extends ActivatePowerPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {

        Map<String, Object> map = buf.readMap(
                PacketByteBuf::readString,
                ActivatePowerPacket::readObject
        );
//
//        FireTraceAttackEntity fireAttack = FireTraceAttackEntity.fromMap(map, player.getWorld());
//
//        shootEntity(player, fireAttack, (double) map.get("speed"));
//
//        getServerWorld(server, player).spawnEntity(fireAttack);
    }
}
