package com.yourname.yourmod.api.system.network;

public final class MessageSpec<T> {

    private final String id;
    private final Codec<T> codec;
    private final MessageHandler<T> handler;

    public MessageSpec(String id, Codec<T> codec, MessageHandler<T> handler) {
        this.id = id;
        this.codec = codec;
        this.handler = handler;
    }

    public String id() {
        return id;
    }

    public Codec<T> codec() {
        return codec;
    }

    public MessageHandler<T> handler() {
        return handler;
    }
}
