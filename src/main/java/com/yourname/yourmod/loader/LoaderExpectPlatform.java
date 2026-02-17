package com.yourname.yourmod.loader;

public interface LoaderExpectPlatform {

    String id(String path);
    boolean isClient();

    Registries registries();
    Network network();
    Events events();

    interface Registries {
        <T> void item(String name, T item);
        <T> void block(String name, T block);
        <T, C> void entity(String name, T entityType, C category, float width, float height);
        <T, B> void blockEntity(String name, T blockEntityType, B... blocks);
    }

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
