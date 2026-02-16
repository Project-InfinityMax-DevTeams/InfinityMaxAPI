package com.yourname.yourmod.api.system.dsl;

import com.yourname.yourmod.api.system.ModSystem;
import com.yourname.yourmod.api.system.SystemRegistry;

/**
 * DSL宣言と実行システムの接続のみを担うクラス。
 */
public final class DataDrivenLoader {

    private final SystemRegistry registry;
    private final DslDefinitionSource source;

    public DataDrivenLoader(SystemRegistry registry, DslDefinitionSource source) {
        this.registry = registry;
        this.source = source;
    }

    public void bind() {
        for (DslDefinition definition : source.load()) {
            ModSystem system = registry.find(definition.systemId())
                    .orElseThrow(() -> new IllegalStateException(
                            "System not found for DSL: " + definition.systemId()
                    ));

            if (system instanceof ConfigurableSystem configurable) {
                configurable.configure(definition);
            } else {
                definition.apply(system);
            }
        }
    }
}
