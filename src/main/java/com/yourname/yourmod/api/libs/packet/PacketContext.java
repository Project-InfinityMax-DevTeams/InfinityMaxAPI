package com.yourname.yourmod.api.libs.packet;

public final class PacketContext {

    private final Object player;
    private final boolean isClient;

    public PacketContext(Object player, boolean isClient) {
        this.player = player;
        this.isClient = isClient;
    }

    public Object player() {
        return player;
    }

    public boolean isClient() {
        return isClient;
    }
}