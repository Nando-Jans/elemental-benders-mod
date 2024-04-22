package com.example.networking;

import com.example.ElementalBendersMod;
import net.minecraft.util.Identifier;

public class ModMessagesIdentifiers {

    // Player
    public static final Identifier PLAYER_JOIN = new Identifier(ElementalBendersMod.MOD_ID, "player_join");

    // NBT synchronization
    public static final Identifier SYNC_NBT = new Identifier(ElementalBendersMod.MOD_ID, "sync_nbt");
    public static final Identifier REQUEST_SYNC_NBT = new Identifier(ElementalBendersMod.MOD_ID, "request_sync_nbt");

    // Fire attacks
    public static final Identifier FIRE_ATTACK = new Identifier(ElementalBendersMod.MOD_ID, "fire_attack");
    public static final Identifier FIRE_FLIGHT_ABILITY = new Identifier(ElementalBendersMod.MOD_ID, "fire_flight_ability");

    // Attacks
    public static final Identifier ABILITY = new Identifier(ElementalBendersMod.MOD_ID, "ability");

    // Fire particles
    public static final Identifier SPAWN_PARTICLE = new Identifier(ElementalBendersMod.MOD_ID, "spawn_particle");
}
