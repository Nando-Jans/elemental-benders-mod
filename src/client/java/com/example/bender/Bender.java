package com.example.bender;

import com.example.util.BendingData;
import com.example.util.EntityDataSaver;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

abstract public class Bender {

    public static int getLevel(PlayerEntity player) {
        // Get the player's fire bending level
        return BendingData.getBendingLevel((EntityDataSaver) player);
    }

    public static void attack(MinecraftClient client) {
        // Fire bending attack logic
        if (client.player != null) {
            // Switch for different fire bending attacks based on the player's selected hotbar slot
            switch (client.player.getInventory().selectedSlot) {
                case 0 ->
                    // Fire bending attack 1
                        slot0Attacks(client);
                case 1 ->
                    // Fire bending attack 2
                        slot1Attacks(client);
                case 2 ->
                    // Fire bending attack 3
                        slot2Attacks(client);
                case 3 ->
                    // Fire bending attack 4
                        slot3Attacks(client);
                case 4 ->
                    // Fire bending attack 5
                        slot4Attacks(client);
                case 5 ->
                    // Fire bending attack 6
                        slot5Attacks(client);
                case 6 ->
                    // Fire bending attack 7
                        slot6Attacks(client);
                case 7 ->
                    // Fire bending attack 8
                        slot7Attacks(client);
                case 8 ->
                    // Fire bending attack 9
                        slot8Attacks(client);
            }
        }
    }

    public static void slot0Attacks(MinecraftClient client) {
        // Fire bending attack 1
    }

    public static void slot1Attacks(MinecraftClient client) {
        // Fire bending attack 2
    }

    public static void slot2Attacks(MinecraftClient client) {
        // Fire bending attack 3
    }

    public static void slot3Attacks(MinecraftClient client) {
        // Fire bending attack 4
    }

    public static void slot4Attacks(MinecraftClient client) {
        // Fire bending attack 5
    }

    public static void slot5Attacks(MinecraftClient client) {
        // Fire bending attack 6
    }

    public static void slot6Attacks(MinecraftClient client) {
        // Fire bending attack 7
    }

    public static void slot7Attacks(MinecraftClient client) {
        // Fire bending attack 8
    }

    public static void slot8Attacks(MinecraftClient client) {
        // Fire bending attack 9
    }
}
