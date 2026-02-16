package com.yourname.yourmod.api.system.state;

import java.util.Objects;

/**
 * Capability風の状態識別子。
 */
public final class StateKey<T> {

    private final Class<T> type;

    private StateKey(String id, Class<T> type) {
        this.id = id;
        this.type = type;
    }

    public final class StateKey<T> {

        private final Class<T> type;

        private StateKey(Class<T> type) {
            this.type = type;
        }

        public static <T> StateKey<T> create(Class<T> type) {
            return new StateKey<>(type);
        }

        public Class<T> type() {
            return type;
        }
    }

    public Class<T> type() {
        return type;
    }

}
