package com.example.command;

import com.example.networking.Client2ServerModMessages;
import com.example.util.BendingData;
import com.example.util.EntityDataSaver;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class BendingConfigCommand {

    public static final Logger LOGGER = LoggerFactory.getLogger("bending config command");

    // /bending <targets> setElement <type>
    private static int setType(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity target = EntityArgumentType.getPlayer(context, "target");
        PlayerEntity player = context.getSource().getPlayer();

        if (target == null) {
            LOGGER.error("Target is null");
            return 0;
        }

        String bendingType = StringArgumentType.getString(context, "element");
        BendingData.setBendingType((EntityDataSaver) target, bendingType);
        ServerPlayNetworking.send(
                target, Client2ServerModMessages.SYNC_NBT,
                PacketByteBufs
                        .create()
                        .writeNbt(BendingData.get((EntityDataSaver) target))
        );
        context.getSource().sendMessage(Text.literal("Bending type of player " + target.getName() + " is set to " + bendingType));

        LOGGER.info("Bending type of " + target + " set to " + bendingType);

        return 1;
    }

    // /bending <targets> getElement <type>
    private static int getType(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity target = EntityArgumentType.getPlayer(context, "target");
        PlayerEntity player = context.getSource().getPlayer();

        if (target == null) {
            LOGGER.error("Player is null");
            return 0;
        }

        String bendingType = BendingData.getBendingType((EntityDataSaver) player);
        context.getSource().sendMessage(Text.literal("Bending type of player " + target.getName() + " is " + bendingType));
        LOGGER.info("Running bending config command " + context.getInput());
        return 1;
    }

    private static int setLevel(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        PlayerEntity target = EntityArgumentType.getPlayer(context, "target");
        PlayerEntity player = context.getSource().getPlayer();

        if (target == null) {
            LOGGER.error("Target is null");
            return 0;
        }

        int bendingLevel = IntegerArgumentType.getInteger(context, "level");
        BendingData.setBendingLevel((EntityDataSaver) target, bendingLevel);
        context.getSource().sendMessage(Text.literal("Bending level of player " + target.getName() + " is set to " + bendingLevel));

        LOGGER.info("Bending type of " + target + " set to " + bendingLevel);

        return 1;
    }

    private static int getLevel(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        PlayerEntity target = EntityArgumentType.getPlayer(context, "target");
        PlayerEntity player = context.getSource().getPlayer();

        if (target == null) {
            LOGGER.error("Player is null");
            return 0;
        }


        int bendingLevel = BendingData.getBendingLevel((EntityDataSaver) player);
        context.getSource().sendMessage(Text.literal("Bending level of player " + target.getName() + " is " + bendingLevel));
        LOGGER.info("Running bending config command " + context.getInput());
        return 1;
    }

    private static final SuggestionProvider<ServerCommandSource> BENDING_TYPE_SUGGESTION_PROVIDER = (context, builder) -> CommandSource.suggestMatching(BendingData.BENDING_TYPES, builder);

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registry, CommandManager.RegistrationEnvironment environment) {
        // Register the command
        dispatcher.register(
                CommandManager.literal("bending")
                        .requires(source -> source.hasPermissionLevel(2))

                        .then(CommandManager.argument("target", EntityArgumentType.player())

                            .then(CommandManager.literal("getType")
                                    .executes(BendingConfigCommand::getType))

                            .then(CommandManager.literal("setType")
                                    .then(CommandManager.argument("element", StringArgumentType.string())
                                            .suggests(BENDING_TYPE_SUGGESTION_PROVIDER)
                                    .executes(BendingConfigCommand::setType)))

                            .then(CommandManager.literal("getLevel")
                                    .executes(BendingConfigCommand::getLevel))

                            .then(CommandManager.literal("setLevel")
                                    .then(CommandManager.argument("level", IntegerArgumentType.integer()))
                                    .executes(BendingConfigCommand::setLevel))

                        ));
    }
}
