package com.yourname.yourmod.api.client;

import com.yourname.yourmod.api.lifecycle.LifecycleRegistry;
import com.yourname.yourmod.api.lifecycle.ModLifecycle;

import java.util.function.Consumer;

public final class Client {

    private Client() {}

    public static void init(Consumer<ClientContext> consumer) {
        LifecycleRegistry.register(ModLifecycle.CLIENT_INIT, () -> {
            consumer.accept(new ClientContext());
        });
    }
}