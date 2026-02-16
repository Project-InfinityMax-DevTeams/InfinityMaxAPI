package com.yourname.yourmod.api.system.state;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 1オブジェクトに紐づく状態コンテナ。
 */
public final class StateContainer {

    private final Map<StateKey<?>, Object> values = new ConcurrentHashMap<>();

    public <T> void put(StateKey<T> key, T value) {
        if (value == null) {
            throw new IllegalArgumentException("State value cannot be null");
        }
        values.put(key, value);
    }

    public <T> T get(StateKey<T> key) {
        Object value = values.get(key);
        if (value == null) {
            return null;
        }
        return key.type().cast(value);
    }

    public <T> T getOrDefault(StateKey<T> key, T defaultValue) {
        T value = get(key);
        return value != null ? value : defaultValue;
    }

    public <T> void remove(StateKey<T> key) {
        values.remove(key);
    }

    public void clear() {
        values.clear();
    }
}