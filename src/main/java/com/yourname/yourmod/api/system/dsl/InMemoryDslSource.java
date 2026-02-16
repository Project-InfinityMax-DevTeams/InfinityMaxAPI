package com.yourname.yourmod.api.system.dsl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 開発時にDSL定義を明示的に注入するためのソース。
 */
public final class InMemoryDslSource implements DslDefinitionSource {

    private final List<DslDefinition> definitions = new ArrayList<>();

    public InMemoryDslSource add(DslDefinition definition) {
        definitions.add(definition);
        return this;
    }

    @Override
    public Collection<DslDefinition> load() {
        return List.copyOf(definitions);
    }
}
