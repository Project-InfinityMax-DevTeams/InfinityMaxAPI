package com.yourname.yourmod.loader;

public final class Platform {
    private static LoaderExpectPlatform instance;

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
}