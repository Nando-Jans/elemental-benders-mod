package com.example.event;

import com.example.ElementalBendersMod;
import com.example.bender.elements.fire.FireBender;
import com.example.bender.tree.BendingTreeHandler;
import com.example.gui.screen.BendingTreeScreen;
import com.example.networking.Client2ServerModMessages;
import com.example.util.BendingData;
import com.example.util.EntityDataSaver;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String BENDING_CATEGORY = "key.category.elementalbenders.bending";
    public static final String TOGGLE_BENDING_KEY = "key.elementalbenders.toggle_bending";
    public static final String OPEN_BENDING_TREE_KEY = "key.elementalbenders.open_bending_tree";

    public static KeyBinding toggleBendingKey;
    public static KeyBinding openBendingTreeKey;

    public static void registerKeyInputs() {
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            // Test if the player is holding nothing and the attack key is pressed
            PlayerEntity player = client.player;

            if (player == null) {
                return;
            }

            if (
                    BendingData.getBendingToggle((EntityDataSaver) player) &&
                    client.options.attackKey.isPressed()
            ) {
                attackKey(client, player);
            }

            if (toggleBendingKey.wasPressed()) {
                toggleBendingKey(client, player);
            }

            if (openBendingTreeKey.wasPressed()) {
                openBendingTreeKey(client, player);
            }

            if (!client.options.jumpKey.isPressed()) {
                stopJumpKey(client, player);
            }

            if (client.options.jumpKey.wasPressed()) {
                jumpKey(client, player);
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {


        });
    }

    private static void attackKey(MinecraftClient client , PlayerEntity player) {
        ElementalBendersMod.LOGGER.info("Attack key pressed");
        String bendingType = BendingData.getBendingType((EntityDataSaver) player);

        switch (bendingType) {
            case BendingData.FIRE_BENDING:
                FireBender.attack(client);

                break;
            case BendingData.WATER_BENDING:

                break;
            case BendingData.EARTH_BENDING:

                break;
            case BendingData.AIR_BENDING:

                break;
        }
    }

    private static void toggleBendingKey(MinecraftClient client, PlayerEntity player) {
        ElementalBendersMod.LOGGER.info("Toggle bending key pressed");
        // Toggle bending for the player
        BendingData.toggleBending((EntityDataSaver) player);
        boolean bendingToggle = (BendingData.getBendingToggle((EntityDataSaver) player));
        player.sendMessage(
                Text.literal("Bending toggled " + (bendingToggle ? "on" : "off"))
                        .fillStyle(Style.EMPTY.withColor(bendingToggle ? Formatting.GREEN : Formatting.RED))
                        .fillStyle(Style.EMPTY.withBold(true)),
                true
        );

        // Sync the player's nbt data with the server
        ClientPlayNetworking.send(Client2ServerModMessages.SYNC_NBT, PacketByteBufs.create().writeNbt(BendingData.get((EntityDataSaver) player)));
    }

    private static void openBendingTreeKey(MinecraftClient client, PlayerEntity player) {
        client.setScreen(new BendingTreeScreen(BendingTreeHandler.valueOf(BendingTreeHandler.Type.FIRE, "")));
    }

    private static void jumpKey(MinecraftClient client, PlayerEntity player) {
        // Jump logic
        String bendingType = BendingData.getBendingType((EntityDataSaver) player);
        switch (bendingType) {
            case BendingData.FIRE_BENDING:
                FireBender.jump(client);

                break;
            case BendingData.WATER_BENDING:

                break;
            case BendingData.EARTH_BENDING:

                break;
            case BendingData.AIR_BENDING:

                break;
        }
    }

    private static void stopJumpKey(MinecraftClient client, PlayerEntity player) {
        // Stop jump logic
        String bendingType = BendingData.getBendingType((EntityDataSaver) player);

        switch (bendingType) {
            case BendingData.FIRE_BENDING:
                FireBender.stopJump(client);

                break;
            case BendingData.WATER_BENDING:

                break;
            case BendingData.EARTH_BENDING:

                break;
            case BendingData.AIR_BENDING:

                break;
        }
    }

    public static void registerKeyBindings() {
        toggleBendingKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                TOGGLE_BENDING_KEY,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_B,
                BENDING_CATEGORY
        ));

        openBendingTreeKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                OPEN_BENDING_TREE_KEY,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_N,
                BENDING_CATEGORY
        ));
    }
}
