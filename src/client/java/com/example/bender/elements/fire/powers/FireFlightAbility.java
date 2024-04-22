package com.example.bender.elements.fire.powers;

import com.example.bender.elements.fire.FireAttack;
import com.example.networking.Client2ServerModMessages;
import com.example.util.BendingData;
import com.example.util.EntityDataSaver;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

public class FireFlightAbility extends FireAttack {

    public static long jumpedTime = 0;

    public static void go(@NotNull MinecraftClient client) {
        // Fire flight ability logic

        if (jumpedTime < System.currentTimeMillis() - 1000 || client.player == null || client.player.isOnGround()) {
            jumpedTime = System.currentTimeMillis();
            return;
        }

        ClientPlayNetworking.send(
            Client2ServerModMessages.FIRE_FLIGHT_ABILITY,
            PacketByteBufs.create().writeBoolean(true)
        );

        if (client.player != null) {

            float speed = 1.1F;

            BendingData.setFireFlying((EntityDataSaver) client.player, true);

            Vec3d vec3d = client.player.getRotationVector();
            Vec3d vec3d2 = client.player.getVelocity();
            Vec3d velocity = vec3d2.add(vec3d.x * 0.1 + (vec3d.x * 1.5 - vec3d2.x) * speed, vec3d.y * 0.1 + (vec3d.y * 1.5 - vec3d2.y) * speed, vec3d.z * 0.1 + (vec3d.z * 1.5 - vec3d2.z) * speed);
            client.player.setVelocityClient(velocity.x, velocity.y, velocity.z);
        }
    }

    public static void stop(MinecraftClient client) {
        // Fire flight ability logic

        ClientPlayNetworking.send(
            Client2ServerModMessages.FIRE_FLIGHT_ABILITY,
            PacketByteBufs.create().writeBoolean(false)
        );

        if (client.player != null) {
            BendingData.setFireFlying((EntityDataSaver) client.player, false);
        }
    }
}
