package com.yourname.yourmod.api.system.dsl;

import java.util.Collection;
import java.util.List;

/**
 * DSLローダーが未接続な環境向けの既定実装。
 */
public final class EmptyDslSource implements DslDefinitionSource {

    @Override
    public Collection<DslDefinition> load() {
        return List.of();
    }
}
