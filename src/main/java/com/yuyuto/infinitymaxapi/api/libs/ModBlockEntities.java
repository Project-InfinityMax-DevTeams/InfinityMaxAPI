package com.yuyuto.infinitymaxapi.api.libs;

public final class ModBlockEntities {

    private ModBlockEntities() {}

    public static <T, B> T register(String name, T blockEntity, B... blocks) {
        ModRegistries.registerBlockEntity(name, blockEntity, blocks);
        return blockEntity;
    }
}
