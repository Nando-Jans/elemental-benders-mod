package com.example.bender.elements.fire;

import com.example.bender.Bender;
import com.example.bender.elements.fire.powers.*;
import com.example.util.BendingData;
import com.example.util.EntityDataSaver;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;

public class FireBender extends Bender {
    public static void slot0Attacks(MinecraftClient client, PlayerEntity player) {
        // Fire bending attack 1
        if (player.isSneaking())
            FirePunchAttack.attack(client);
        else
            FirePunchAttack.attack(client);
    }

    public static void slot1Attacks(MinecraftClient client, PlayerEntity player) {
        // Fire bending attack 2
        if (player.isSneaking()) {
            FireBurstAttack.attack(client);
        } else {
            FireWaveAttack.attack(client);
        }
    }

    public static void slot2Attacks(MinecraftClient client, PlayerEntity player) {
        // Fire bending attack 3
        if (player.isSneaking()) {
            FireBlastAttack.attack(client);
        } else {
            FireSlashAttack.attack(client);
        }
    }

    public static void slot3Attacks(MinecraftClient client, PlayerEntity player) {
        // Fire bending attack 4
        if (player.isSneaking()) {
            LightningBoltAttack.attack(client);
        } else {
            LightningBoltAttack.attack(client);
        }
    }

    public static void attack(@NotNull MinecraftClient client) {
        // Fire bending attack logic
        if (client.player != null) {
            // Switch for different fire bending attacks based on the player's selected hotbar slot
            switch (client.player.getInventory().selectedSlot) {
                case 0 ->
                    // Fire bending attack 1
                    slot0Attacks(client, client.player);
                case 1 ->
                    // Fire bending attack 2
                    slot1Attacks(client, client.player);
                case 2 ->
                    // Fire bending attack 3
                    slot2Attacks(client, client.player);
                case 3 ->
                    // Fire bending attack 4
                    slot3Attacks(client, client.player);
            }
        }
    }

    public static void jump(MinecraftClient client) {
        if (!jumped && client.player != null && !client.player.isOnGround() && !client.player.getAbilities().flying) {
            // Fire bending jump logic
            if (client.player.isSneaking())
                FireFlightAbility.stop(client);
            else
                FireFlightAbility.go(client);
        }

        if (!BendingData.getFireFlying((EntityDataSaver) client.player)) {
            jumped = true;
        }
    }

    private static boolean jumped = false;

    public static void stopJump(MinecraftClient client) {

        jumped = false;
    }
}
