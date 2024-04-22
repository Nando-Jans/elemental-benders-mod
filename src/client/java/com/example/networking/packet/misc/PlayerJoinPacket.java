package com.example.networking.packet.misc;

import com.example.core.Packet;
import com.example.gui.screen.ChooseBendTypeScreen;
import com.example.networking.Server2ClientModMessages;
import com.example.util.BendingData;
import com.example.util.EntityDataSaver;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class PlayerJoinPacket extends Packet {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
//        if (BendingData.getBendingType((EntityDataSaver) client.player) != null) {
            // Open the bending type selection screen
            client.execute(() -> client.setScreen(new ChooseBendTypeScreen()));
//    }

        // Sync the client's NBT data with the server
//        ClientPlayNetworking.send(
//                Server2ClientModMessages.SYNC_NBT,
//                PacketByteBufs.create().writeNbt(
//                        BendingData.get((EntityDataSaver) client.player)
//                )
//        );
    }
}
