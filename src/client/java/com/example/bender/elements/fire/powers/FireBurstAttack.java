package com.example.bender.elements.fire.powers;

import com.example.bender.elements.fire.FireAttack;
import com.example.entity.bending.fragments.fire.trace.FireTraceAttackEntitySettings;
import com.example.entity.bending.fragments.fire.wave.FireWaveAttackEntitySettings;
import net.minecraft.client.MinecraftClient;
import net.minecraft.world.World;

public class FireBurstAttack extends FireAttack {
    public static void attack(MinecraftClient client) {
        int level = getLevel(client);
        World world = client.world;
        // Fire bending attack logic
        sendAttackPacket(getFireAttackEntitySettings(level, world).toMap());
    }

    // Get the fire attack entity settings
    private static FireWaveAttackEntitySettings getFireAttackEntitySettings(int level, World world) {
        return FireWaveAttackEntitySettings.getInstance(world)
                .setDamage(5.0F)
                .setSetFire(true)
                .setSetFireAge(15)
                .setIsWave(true)
                .setWaveSpeed(1F)
                .setLife(5)
                .setSpeed(0);
    }
}
