package com.yourname.yourmod.api.system.dsl;

import com.yourname.yourmod.api.system.ModSystem;

import java.util.Map;

/**
 * DSL側の宣言情報。
 */
public interface DslDefinition {

    String systemId();

    /**
     * WHAT(宣言)を明示化するための設定マップ。
     */
    Map<String, Object> properties();

    /**
     * 非ConfigurableSystem向けの適用フック。
     */
    default void apply(ModSystem system) {
    }
}
