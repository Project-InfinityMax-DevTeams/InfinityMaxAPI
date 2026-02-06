package com.yourname.yourmod.api.libs.packet;

import com.yourname.yourmod.loader.Platform;

import java.util.ArrayList;
import java.util.List;

public final class PacketRegistry {

    private static final List<SimplePacket<?>> PACKETS = new ArrayList<>();

    public static void register(SimplePacket<?> packet) {
        PACKETS.add(packet);
    }

    public static void init() {
        Platform.get().network().register();
    }

    public static List<SimplePacket<?>> packets() {
        return PACKETS;
    }
}