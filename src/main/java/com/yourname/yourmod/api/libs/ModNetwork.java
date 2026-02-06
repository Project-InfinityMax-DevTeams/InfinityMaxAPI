package com.yourname.yourmod.api.libs;

import com.yourname.yourmod.loader.Platform;
import net.minecraft.server.level.ServerPlayer;

public final class ModNetwork {

    private ModNetwork() {}

    public static void init() {
        Platform.get().initNetwork();
    }

    public static void sendToServer(Object packet) {
        Platform.get().sendToServer(packet);
    }

    public static void sendToClient(ServerPlayer player, Object packet) {
        Platform.get().sendToClient(player, packet);
    }
}