package com.yuyuto.infinitymaxapi.api.libs;

import com.yuyuto.infinitymaxapi.api.libs.registry.ModRegistriesProvider;
import com.yuyuto.infinitymaxapi.api.libs.registry.settings.BlockEntitySettings;

public final class ModBlockEntities {

    private ModBlockEntities() {}

    public static <T, B> T register(String id, T blockEntity, B... blocks) {
        ModRegistriesProvider.get().registerBlockEntity(id, blockEntity, blocks, new BlockEntitySettings());
        return blockEntity;
    }
}
