package com.example.event;

import com.example.util.EntityDataSaver;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public class ModPlayerEventCopyFrom implements ServerPlayerEvents.CopyFrom {

    @Override
    public void copyFromPlayer(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
        EntityDataSaver original = (EntityDataSaver) oldPlayer;
        EntityDataSaver player = (EntityDataSaver) newPlayer;

        copyData(original, player);
    }

    private void copyData(EntityDataSaver original, EntityDataSaver player) {
        player.getPersistentData().putInt("bendingLevel", original.getPersistentData().getInt("bendingLevel"));
        player.getPersistentData().putString("bendingType", original.getPersistentData().getString("bendingType"));
    }
}
