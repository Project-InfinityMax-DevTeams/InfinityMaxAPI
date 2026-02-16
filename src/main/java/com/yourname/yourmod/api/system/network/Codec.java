package com.yourname.yourmod.api.system.network;

public interface Codec<T> {

    T decode(Object buffer);

    void encode(Object buffer, T payload);
}
