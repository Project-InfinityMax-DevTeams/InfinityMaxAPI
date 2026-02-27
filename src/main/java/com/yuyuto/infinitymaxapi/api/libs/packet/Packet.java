package com.yuyuto.infinitymaxapi.api.libs.packet;

import com.yuyuto.infinitymaxapi.loader.Platform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.Objects;

public final class Packet {

    private Packet() {}
    private static final Map<String, SimplePacket<?>> PACKETS = new HashMap<>();

    public static <T> void register(
            String id,
            PacketDirection direction,
            Function<PacketBuffer, T> decoder,
            SimplePacket.PacketEncoder<T> encoder,
            PacketHandler<T> handler
    ) {
        PacketRegistry.register(new SimplePacket<>(id, direction, decoder, encoder, handler));
    }

    public static void register(SimplePacket<?> packet) {
        if (PACKETS.containsKey(packet.id)) {
            throw new IllegalStateException(
                "Packet ID '" + packet.id + "' is already registered"
            );
        }
        PACKETS.put(packet.id, packet);
    }

    public static List<SimplePacket<?>> packets() {
        return new ArrayList<>(PACKETS.values());
    }

    public static <T> void sendToServer(T packet) {
        Platform.get().network().sendToServer(packet);
    }

    public static <T> void sendToPlayer(Object player, T packet) {
        Platform.get().network().sendToPlayer(player, packet);
    }
}
