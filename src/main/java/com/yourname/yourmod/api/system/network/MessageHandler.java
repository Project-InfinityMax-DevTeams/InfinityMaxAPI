package com.yourname.yourmod.api.system.network;

@FunctionalInterface
public interface MessageHandler<T> {

    void handle(T payload, Object context);
}
