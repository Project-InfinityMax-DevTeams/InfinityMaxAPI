package com.yuyuto.infinitymaxapi.loader;

import com.yuyuto.infinitymaxapi.api.libs.ModRegistries;

public interface LoaderExpectPlatform {

    String id(String path);
    boolean isClient();

    ModRegistries registries();
    Network network();
    Events events();

    interface Network {
        void register();
        <T> void sendToServer(T packet);
        <T> void sendToPlayer(T player, T packet);
    }

    interface Events {
        void register();
        <T> void onPlayerJoin(T player);
    }
}
