package com.yuyuto.infinitymaxapi.api.libs.packet;

import java.util.function.Function;

public final class SimplePacket<T> {

    public final String id;
    public final PacketDirection direction;
    public final Function<PacketBuffer, T> decoder;
    public final PacketEncoder<T> encoder;
    public final PacketHandler<T> handler;

    public SimplePacket(
            String id,
            PacketDirection direction,
            Function<PacketBuffer, T> decoder,
            PacketEncoder<T> encoder,
            PacketHandler<T> handler
    ) {
        this.id = id;
        this.direction = direction;
        this.decoder = decoder;
        this.encoder = encoder;
        this.handler = handler;
    }

    @FunctionalInterface
    public interface PacketEncoder<T> {
        void encode(T packet, PacketBuffer buf);
    }
}
