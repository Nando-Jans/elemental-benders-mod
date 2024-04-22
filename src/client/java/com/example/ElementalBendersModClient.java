package com.example;

import com.example.entity.ModEntityRenderer;
import com.example.entity.bending.fragments.fire.lightning.LightningAttackEntity;
import com.example.event.KeyInputHandler;
import com.example.event.ModClientEvents;
import com.example.networking.Server2ClientModMessages;
import com.example.particle.ModClientParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElementalBendersModClient implements ClientModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger("elementalbenders");

	public static final String MOD_ID = "elementalbenders";
	private static final String MOD_NAME = "Elemental Benders";

	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.

		KeyInputHandler.registerKeyBindings();
		KeyInputHandler.registerKeyInputs();

		Server2ClientModMessages.registerS2CPackets();

		ModEntityRenderer.registerEntityRenderers();

		ModClientEvents.register();

		ModClientParticles.register();
	}
}