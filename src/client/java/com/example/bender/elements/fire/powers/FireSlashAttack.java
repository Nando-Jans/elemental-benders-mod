package com.example.bender.elements.fire.powers;

import com.example.bender.elements.fire.FireAttack;
import com.example.entity.bending.fragments.fire.trace.FireTraceAttackEntitySettings;
import net.minecraft.client.MinecraftClient;
import net.minecraft.world.World;

public class FireSlashAttack extends FireAttack {
    public static void attack(MinecraftClient client) {
        int level = getLevel(client);

        World world = client.world;
        // Fire bending attack logic



        sendAttackPacket(getFireAttackEntitySettings(level, world).toMap());
    }

    // Get the fire attack entity settings
    private static FireTraceAttackEntitySettings getFireAttackEntitySettings(int level, World world) {
        return FireTraceAttackEntitySettings.getInstance(world)
                .setDamage(3.0F)
                .setDamageRadius(3.0F)
                .setSetFire(true)
                .setSetFireRadius(2.0F)
                .setSetOnFire(true)
                .setSetOnFireTicks(60)
                .setLife(100)
                .setSpeed(1.5f);
    }
}
