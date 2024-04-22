package com.example.bender.elements.fire.powers;

import com.example.bender.elements.fire.FireAttack;
import com.example.entity.bending.fragments.fire.trace.FireTraceAttackEntitySettings;
import net.minecraft.client.MinecraftClient;
import net.minecraft.world.World;

public class FirePunchAttack extends FireAttack {
    public static void attack(MinecraftClient client) {
        int level = getLevel(client);
        World world = client.world;
        // Fire bending attack logic
        sendAttackPacket(getFireAttackEntitySettings(level, world).toMap());
    }

    // Get the fire attack entity settings
    private static FireTraceAttackEntitySettings getFireAttackEntitySettings(int level, World world) {
        return FireTraceAttackEntitySettings.getInstance(world)
                .setDamage(5.0F + level)
                .setSetOnFire(true)
                .setSetOnFireTicks(40 + 10 * level)
                .setDamageRadius(1.0F)
                .setSpeed(1)
                .setLife(7)
                .setMaxCollisions(100);
    }
}
