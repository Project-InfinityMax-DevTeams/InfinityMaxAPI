package com.yourname.yourmod.loader;

import com.yourname.yourmod.api.libs.internal.*;
public final class Platform {

    private static LoaderExpectPlatform instance;
    private final Registries registries = new Registries();
    private Platform() {}

    public static void set(LoaderExpectPlatform platform) {
        instance = platform;
    }

    public static LoaderExpectPlatform get() {
        if (instance == null) {
            throw new IllegalStateException("Platform not initialized");
        }
        return instance;
    }

    public static Platform get() {
        return INSTANCE;
    }

    public Registries registries() {
        return registries;
    }

    public static final class Registries {
        public <T> void item(String id, T item) {
            //
        }

        public <T> void block(String id, T block) {
            //
        }

        public <T, C> void entity(String id, T entity, C category, float width, float height) {
            //
        }

        public <T, B> void blockEntity(String id, T entity, B... blocks) {
            //
        }
    }
}