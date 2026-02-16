package com.yourname.yourmod.api.system.state;

import java.util.Objects;

/**
 * Capability風の状態識別子。
 */
public final class StateKey<T> {

    private final String id;
    private final Class<T> type;

    private StateKey(String id, Class<T> type) {
        this.id = id;
        this.type = type;
    }

    public static <T> StateKey<T> of(String id, Class<T> type) {
        return new StateKey<>(id, type);
    }

    public String id() {
        return id;
    }

    public Class<T> type() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StateKey<?> stateKey)) return false;
        return Objects.equals(id, stateKey.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
