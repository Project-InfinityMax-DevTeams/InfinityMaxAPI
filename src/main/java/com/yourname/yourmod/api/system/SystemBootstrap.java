package com.yourname.yourmod.api.system;

import com.yourname.yourmod.api.system.dsl.DataDrivenLoader;
import com.yourname.yourmod.api.system.dsl.DslDefinitionSource;

/**
 * SystemRegistry中心の初期化フロー。
 */
public final class SystemBootstrap {

    private final SystemRegistry registry;
    private final SystemRuntime runtime;
    private final DataDrivenLoader dataDrivenLoader;

    public SystemBootstrap(SystemRegistry registry, SystemRuntime runtime, DslDefinitionSource source) {
        this.registry = registry;
        this.runtime = runtime;
        this.dataDrivenLoader = new DataDrivenLoader(registry, source);
    }

    public void start() {
        dataDrivenLoader.bind();
        runtime.eventBridge().registerForwarders();
        registry.initializeAll(runtime);
    }

    public void stop() {
        registry.shutdownAll();
    }
}
