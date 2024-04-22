package com.example.util;

import com.example.command.BendingConfigCommand;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class CommandRegistries {
    public static void register() {
        // Register the command
        CommandRegistrationCallback.EVENT.register(BendingConfigCommand::register);

    }
}
