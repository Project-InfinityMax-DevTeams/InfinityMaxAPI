package com.yourname.yourmod.api.libs;

import com.yourname.yourmod.loader.LoaderExpectPlatform;

public final class ModRegistries {

    private static LoaderExpectPlatform.Registries platformRegistries;

    public ModRegistries(LoaderExpectPlatform.Registries platformRegistries) {
        this.platformRegistries = platformRegistries;
    }

    public static <T> void registerItem(String id, T item) {
        platformRegistries.item(id, item);
    }

    public static <T> void registerBlock(String id, T block, float strength, boolean noOcclusion) {
        platformRegistries.block(id, block);
    }

    public static <T, C> void registerEntity(String id, T entityType, C category, float width, float height) {
        platformRegistries.entity(id, entityType, category, width, height);
    }

    public static <T, B> void registerBlockEntity(String id, T blockEntityType, B... blocks) {
        platformRegistries.blockEntity(id, blockEntityType, blocks);
    }
}