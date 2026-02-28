package com.yuyuto.infinitymaxapi.api.libs.packet;

@FunctionalInterface
public interface PacketHandler<T> {
    void handle(T packet, PacketContext context);
}