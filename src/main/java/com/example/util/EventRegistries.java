package com.example.util;

import com.example.event.ModPlayerEventCopyFrom;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;

public class EventRegistries {
    public static void register() {
        // Register the event
        ServerPlayerEvents.COPY_FROM.register(new ModPlayerEventCopyFrom());
    }
}
