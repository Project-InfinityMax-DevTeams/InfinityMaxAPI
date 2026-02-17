package com.yourname.yourmod.api.platform

import com.yourname.yourmod.api.libs.internal.*;

public final class Platform {
    private static final Platform INSTANCE = new Platform();
    private final Registries registries = new Registries();

    private Platform() {}

    public static Platform get() {
        return INSTANCE;
    }

    public Registries registries() {
        return registries;
    }

    public static final class Registries {
        public <T> void item(String id, T item) {
            // ダミー: とりあえず何もしない
        }

        public <T> void block(String id, T block) {
            // ダミー
        }

        public <T, C> void entity(String id, T entity, C category, float width, float height) {
            // ダミー
        }

        public <T, B> void blockEntity(String id, T entity, B... blocks) {
            // ダミー
        }
    }
}
