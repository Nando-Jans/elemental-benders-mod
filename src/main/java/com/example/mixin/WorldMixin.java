package com.example.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public abstract class WorldMixin {
	@Shadow public abstract PlayerManager getPlayerManager();

	@Inject(at = @At("HEAD"), method = "loadWorld")
	private void init(CallbackInfo info) {
		// Iterate through all players
		for (PlayerEntity player : ((MinecraftServer) (Object) this).getPlayerManager().getPlayerList()) {
			// Detect if the player clicks the left mouse button

		}
	}
}