package com.yourname.yourmod.loader.forge;

import com.yourname.yourmod.event.CommonEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;

public final class ForgeEventsImpl {

    public static void register(CommonEvents events) {
        MinecraftForge.EVENT_BUS.addListener(
                (PlayerEvent.PlayerLoggedInEvent e) ->
                        ModEventBus.post(new PlayerJoinEvent(player));
        );
    }
}