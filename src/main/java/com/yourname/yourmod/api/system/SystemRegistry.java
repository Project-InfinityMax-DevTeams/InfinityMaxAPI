package com.yourname.yourmod.api.system;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * DSLで宣言されたシステムと実行システムを一元管理するレジストリ。
 */
public final class SystemRegistry {

    private final Map<String, ModSystem> systems = new LinkedHashMap<>();

    public void register(ModSystem system) {
        ModSystem previous = systems.putIfAbsent(system.id(), system);
        if (previous != null) {
            throw new IllegalStateException("Duplicate system id: " + system.id());
        }
    }

    public Optional<ModSystem> find(String id) {
        return Optional.ofNullable(systems.get(id));
    }

    public Collection<ModSystem> all() {
        return Collections.unmodifiableCollection(systems.values());
    }

    public void initializeAll(SystemRuntime runtime) {
        for (ModSystem system : systems.values()) {
            system.initialize(runtime);
        }
    }

    public void shutdownAll() {
        for (ModSystem system : systems.values()) {
            system.shutdown();
        }
    }
}
