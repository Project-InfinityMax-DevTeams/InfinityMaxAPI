package com.yourname.yourmod.api.system.dsl;

import com.yourname.yourmod.api.system.ModSystem;

import java.util.Map;

/**
 * DSL側の宣言情報。
 */
public interface DslDefinition {

    String systemId();

    void apply(ModSystem system);
}
