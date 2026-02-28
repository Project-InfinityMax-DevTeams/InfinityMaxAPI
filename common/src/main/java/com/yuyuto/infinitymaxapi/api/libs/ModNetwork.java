package com.yuyuto.infinitymaxapi.api.libs;

import com.yuyuto.infinitymaxapi.loader.Platform;

public final class ModNetwork {

    private ModNetwork() {}

    public static void init() {
        Platform.get().network().register();
    }

    public static void sendToServer(Object packet) {
        Platform.get().network().sendToServer(packet);
    }

    public static void sendToClient(Object player, Object packet) {
        Platform.get().network().sendToPlayer(player, packet);
    }
}
