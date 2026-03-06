package com.yuyuto.infinitymaxapi.api.libs;

import com.yuyuto.infinitymaxapi.api.libs.internal.ClientBuilder;
import java.util.function.Consumer;

public final class Client {

    private Client() {}

    public static void init(Consumer<ClientBuilder> consumer) {
        ClientBuilder builder = new ClientBuilder();
        consumer.accept(builder);
        builder.registerAll();
    }
}
