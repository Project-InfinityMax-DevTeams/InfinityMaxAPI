package com.yourname.yourmod.api.system.dsl;

import java.util.Collection;

public interface DslDefinitionSource {

    Collection<DslDefinition> load();
}
