package com.example.util;

import net.minecraft.nbt.NbtCompound;

public class BendingData {
    public static final String FIRE_BENDING = "fire";
    public static final String WATER_BENDING = "water";
    public static final String EARTH_BENDING = "earth";
    public static final String AIR_BENDING = "air";

    public static final String[] BENDING_TYPES = new String[] {
            FIRE_BENDING,
            WATER_BENDING,
            EARTH_BENDING,
            AIR_BENDING
    };

    public static void setBendingType(EntityDataSaver player, String TYPE) {
        NbtCompound nbt = player.getPersistentData();
        nbt.putString("bendingType", TYPE);
    }

    public static String getBendingType(EntityDataSaver player) {
        NbtCompound nbt = player.getPersistentData();
        return nbt.getString("bendingType");
    }

    public static void setBendingLevel(EntityDataSaver player, int level) {
        NbtCompound nbt = player.getPersistentData();
        nbt.putInt("bendingLevel", level);
    }

    public static void incrementBendingLevel(EntityDataSaver player) {
        NbtCompound nbt = player.getPersistentData();
        int level = nbt.getInt("bendingLevel");
        nbt.putInt("bendingLevel", level + 1);
    }

    public static int getBendingLevel(EntityDataSaver player) {
        NbtCompound nbt = player.getPersistentData();
        return nbt.getInt("bendingLevel");
    }

    public static void toggleBending(EntityDataSaver player) {
        NbtCompound nbt = player.getPersistentData();
        boolean toggle = nbt.getBoolean("bendingToggle");
        nbt.putBoolean("bendingToggle", !toggle);
    }

    public static void setBendingToggle(EntityDataSaver player, boolean toggle) {
        NbtCompound nbt = player.getPersistentData();
        nbt.putBoolean("bendingToggle", toggle);
    }

    public static boolean getBendingToggle(EntityDataSaver player) {
        NbtCompound nbt = player.getPersistentData();
        return nbt.getBoolean("bendingToggle");
    }

    public static void setFireFlying(EntityDataSaver player, boolean flying) {
        NbtCompound nbt = player.getPersistentData();
        nbt.putBoolean("fireFlying", flying);
    }

    public static boolean getFireFlying(EntityDataSaver player) {
        NbtCompound nbt = player.getPersistentData();
        return nbt.getBoolean("fireFlying");
    }

    public static void setBendingTree(EntityDataSaver player, String tree) {
        NbtCompound nbt = player.getPersistentData();
        nbt.putString("bendingTree", tree);
    }

    public static String getBendingTree(EntityDataSaver player) {
        NbtCompound nbt = player.getPersistentData();
        return nbt.getString("bendingTree");
    }

    public static void set(EntityDataSaver player, NbtCompound nbt) {
        // Save the NBT data to the player's persistent data for every value in the NBT
        for (String key : nbt.getKeys()) {
            player.getPersistentData().put(key, nbt.get(key));
        }
    }

    public static NbtCompound get(EntityDataSaver player) {
        // Return the player's persistent data
        return player.getPersistentData();
    }
}
