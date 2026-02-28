package com.yuyuto.infinitymaxapi.api.event;

public final class CommonEvents {

    private CommonEvents() {}

    public static void onPlayerJoin(Object player) {
        ModEventBus.post(new PlayerJoinEvent(player));
    }

    public static void onWorldLoad() {
    }
}
