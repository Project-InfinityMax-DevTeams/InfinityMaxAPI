package com.yourname.yourmod;

import com.yourname.yourmod.api.system.ModSystem;
import com.yourname.yourmod.api.system.SystemBootstrap;
import com.yourname.yourmod.api.system.SystemRegistry;
import com.yourname.yourmod.api.system.dsl.DslDefinitionSource;
import com.yourname.yourmod.api.system.dsl.EmptyDslSource;
import com.yourname.yourmod.api.system.dsl.InMemoryDslSource;
import com.yourname.yourmod.api.system.dsl.MapDslDefinition;
import com.yourname.yourmod.api.system.example.HeartbeatSystem;
import com.yourname.yourmod.loader.bridge.LoaderRuntime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class YourMod {

    private static final SystemRegistry SYSTEM_REGISTRY = new SystemRegistry();
    private static final List<ModSystem> PENDING_SYSTEMS = new ArrayList<>();

    private static DslDefinitionSource dslSource = new EmptyDslSource();
    private static SystemBootstrap systemBootstrap;
    private static LoaderRuntime runtime;
    private static boolean bootstrapped;

    private YourMod() {}

    public static SystemRegistry systems() {
        return SYSTEM_REGISTRY;
    }

    /**
     * 実運用時にDSL供給元を差し替える。
     */
    public static void useDslSource(DslDefinitionSource source) {
        if (bootstrapped) {
            throw new IllegalStateException("Cannot change DSL source after init");
        }
        dslSource = source;
    }

    /**
     * init前にシステムを登録キューへ積む。
     */
    public static void queueSystem(ModSystem system) {
        if (bootstrapped) {
            throw new IllegalStateException("Cannot queue systems after init");
        }
        PENDING_SYSTEMS.add(system);
    }

    public static LoaderRuntime runtime() {
        if (runtime == null) {
            throw new IllegalStateException("Runtime not initialized");
        }
        return runtime;
    }

    private static void installDefaultsIfNeeded() {
        if (!PENDING_SYSTEMS.isEmpty()) {
            return;
        }

        queueSystem(new HeartbeatSystem());
        if (dslSource instanceof EmptyDslSource) {
            useDslSource(new InMemoryDslSource().add(new MapDslDefinition(
                    "heartbeat",
                    Map.of(
                            "intervalTicks", 40,
                            "saveSectionId", "heartbeat",
                            "networkMessageId", "heartbeat/ping",
                            "stateKeyId", "yourmod:heartbeat_count"
                    )
            )));
        }
    }

    public static void init() {
        if (bootstrapped) {
            return;
        }

        installDefaultsIfNeeded();

        for (ModSystem system : PENDING_SYSTEMS) {
            SYSTEM_REGISTRY.register(system);
        }

        runtime = new LoaderRuntime();
        systemBootstrap = new SystemBootstrap(
                SYSTEM_REGISTRY,
                runtime,
                dslSource
        );
        systemBootstrap.start();
        bootstrapped = true;
    }
}
