package com.yourname.yourmod.api.system.dsl;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 実運用時の最小DSL定義(プロパティ辞書)。
 */
public final class MapDslDefinition implements DslDefinition {

    private final String systemId;
    private final Map<String, Object> properties;

    public MapDslDefinition(String systemId, Map<String, Object> properties) {
        this.systemId = systemId;
        this.properties = new LinkedHashMap<>(properties);
    }

    @Override
    public String systemId() {
        return systemId;
    }

    @Override
    public Map<String, Object> properties() {
        return Collections.unmodifiableMap(properties);
    }

    public int intValue(String key, int defaultValue) {
        Object value = properties.get(key);
        if (value instanceof Number number) {
            return number.intValue();
        }
        return defaultValue;
    }

    public String stringValue(String key, String defaultValue) {
        Object value = properties.get(key);
        return value == null ? defaultValue : String.valueOf(value);
    }
}
