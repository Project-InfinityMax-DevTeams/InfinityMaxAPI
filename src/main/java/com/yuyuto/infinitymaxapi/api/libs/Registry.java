package com.yuyuto.infinitymaxapi.api.libs;

import java.util.function.Consumer;

public final class Registry {

    private Registry() {}

    public static void registry(Consumer<RegistryScope> block) {
        RegistryApi.registryJava(block);
    }
}
