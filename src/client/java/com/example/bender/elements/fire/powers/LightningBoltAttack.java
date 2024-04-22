package com.example.bender.elements.fire.powers;

import com.example.bender.elements.fire.FireAttack;
import com.example.effect.ModStatusEffects;
import com.example.entity.bending.fragments.fire.lightning.LightningAttackEntitySettings;
import net.minecraft.client.MinecraftClient;
import net.minecraft.world.World;

public class LightningBoltAttack extends FireAttack {
    public static void attack(MinecraftClient client) {
        int level = getLevel(client);

        World world = client.world;
        // Fire bending attack logic

        sendAttackPacket(getLightningAttackEntitySettings(level, world).toMap());
    }

    // Get the fire attack entity settings
    private static LightningAttackEntitySettings getLightningAttackEntitySettings(int level, World world) {
        return LightningAttackEntitySettings.getInstance(world)
                .setDamage(5.0F)
                .setDamageRadius(1.0F)
                .setChainDamageFalloff(0.75F)
                .setChains(true)
                .setChainLength(3)
                .setChainSplitAmount(2)
                .setChainRange(10)
                .setLife(40)
                .setMaxCollisions(1)
                .setSpeed(2f)
                .addStatusEffect(ModStatusEffects.LIGHTNING_BOLT, 40, 1);
    }
}
