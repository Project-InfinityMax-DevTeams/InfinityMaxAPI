package com.yourname.yourmod.api.system.state;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 1オブジェクトに紐づく状態コンテナ。
 */
public final class StateContainer {

    private final Map<StateKey<?>, Object> values = new ConcurrentHashMap<>();

    public <T> void put(StateKey<T> key, T value) {
        values.put(key, value);
    }

    public <T> Optional<T> get(StateKey<T> key) {
        Object value = values.get(key);
        if (value == null) {
            return Optional.empty();
        }
        return Optional.of(key.type().cast(value));
    }

    public <T> void remove(StateKey<T> key) {
        values.remove(key);
    }
}
