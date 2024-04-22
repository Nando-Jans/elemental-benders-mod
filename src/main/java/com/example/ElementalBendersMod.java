package com.example;

import com.example.effect.ModStatusEffects;
import com.example.entity.ModEntities;
import com.example.event.ModEvents;
import com.example.networking.Client2ServerModMessages;
import com.example.particle.ModParticles;
import com.example.util.CommandRegistries;
import com.example.util.EventRegistries;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ElementalBendersMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("elementalbenders");

	public static final String MOD_ID = "elementalbenders";
	private static final String MOD_NAME = "Elemental Benders";


	@Override
	public void onInitialize() {

		ModEntities.registerModEntities();
		ModEvents.registerEvents();

		Client2ServerModMessages.registerC2SPackets();

		// Registries
		CommandRegistries.register();
		EventRegistries.register();
		ModParticles.register();
		ModStatusEffects.register();
	}
}