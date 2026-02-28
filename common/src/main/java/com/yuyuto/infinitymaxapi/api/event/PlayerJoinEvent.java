package com.yuyuto.infinitymaxapi.api.event;

public final class PlayerJoinEvent implements ModEvent {
    public final Object player;

    public PlayerJoinEvent(Object player) {
        this.player = player;
    }
}