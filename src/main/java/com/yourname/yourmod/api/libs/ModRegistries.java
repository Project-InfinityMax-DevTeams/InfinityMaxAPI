package com.yourname.yourmod.api.libs;

import com.yourname.yourmod.loader.Platform;

public final class ModRegistries {

    private ModRegistries() {}

    public static <T> void registerItem(String id, T item) {
        Platform.get().registries().item(id, item);
    }

    public static <T> void registerBlock(String id, T block) {
        Platform.get().registries().block(id, block);
    }

    public static <T> void registerEntity(String id, T entityType) {
        Platform.get().registries().entity(id, entityType);
    }

    public static <T, B> void registerBlockEntity(
        String id,
        T blockEntityType,
        B... blocks
    ) {
        Platform.get().registries().blockEntity(id, blockEntityType, blocks);
    }
}