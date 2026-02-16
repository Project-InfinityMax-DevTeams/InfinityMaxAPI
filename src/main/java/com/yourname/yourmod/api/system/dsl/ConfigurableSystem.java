package com.yourname.yourmod.api.system.dsl;

import com.yourname.yourmod.api.system.ModSystem;

/**
 * DSL定義を受け取れるシステム。
 */
public interface ConfigurableSystem extends ModSystem {

    void configure(DslDefinition definition);
}
