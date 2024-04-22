package com.example.networking.packet.powers.fire;

import com.example.ElementalBendersMod;
import com.example.core.ActivatePowerPacket;
import com.example.entity.bending.fragments.AttackEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class AbilityPacket extends ActivatePowerPacket {
    public static <PacketSender> void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {

        Map<String, Object> map = buf.readMap(
                PacketByteBuf::readString,
                ActivatePowerPacket::readObject


        );

        AttackEntity attack;

        try {
            attack = AttackEntity.fromMap(map, player.getWorld());
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            return;
        }

        shootEntity(player, attack, (float) map.get("speed"));

        getServerWorld(server, player).spawnEntity(attack);
    }
}
