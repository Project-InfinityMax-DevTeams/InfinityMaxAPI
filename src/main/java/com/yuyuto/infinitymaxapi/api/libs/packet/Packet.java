package com.yuyuto.infinitymaxapi.api.libs.packet;

import com.yuyuto.infinitymaxapi.loader.Platform;

import java.util.function.Function;

public final class Packet {

    private Packet() {}

    public static <T> void register(
            String id,
            PacketDirection direction,
            Function<PacketBuffer, T> decoder,
            SimplePacket.PacketEncoder<T> encoder,
            PacketHandler<T> handler
    ) {
        PacketRegistry.register(new SimplePacket<>(id, direction, decoder, encoder, handler));
    }

    public static <T> void sendToServer(T packet) {
        Platform.get().network().sendToServer(packet);
    }

    public static <T> void sendToPlayer(Object player, T packet) {
        Platform.get().network().sendToPlayer(player, packet);
    }
}
