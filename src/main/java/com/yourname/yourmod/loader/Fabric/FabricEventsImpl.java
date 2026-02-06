package com.yourname.yourmod.loader.fabric;

import com.yourname.yourmod.event.CommonEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public final class FabricEventsImpl {

    public static void register(CommonEvents events) {
        ServerPlayConnectionEvents.JOIN.register(
                (handler, sender, server) ->
                        events.onPlayerJoin(handler.player)
        );
    }
}