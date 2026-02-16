package com.yourname.yourmod;

import com.yourname.yourmod.api.system.SystemBootstrap;
import com.yourname.yourmod.api.system.SystemRegistry;
import com.yourname.yourmod.api.system.dsl.EmptyDslSource;
import com.yourname.yourmod.loader.bridge.LoaderRuntime;

public final class YourMod {

    private static final SystemRegistry SYSTEM_REGISTRY = new SystemRegistry();
    private static final SystemBootstrap SYSTEM_BOOTSTRAP = new SystemBootstrap(
            SYSTEM_REGISTRY,
            new LoaderRuntime(),
            new EmptyDslSource()
    );

    private YourMod() {}

    public static SystemRegistry systems() {
        return SYSTEM_REGISTRY;
    }

    public static void init() {
        SYSTEM_BOOTSTRAP.start();
    }
}
