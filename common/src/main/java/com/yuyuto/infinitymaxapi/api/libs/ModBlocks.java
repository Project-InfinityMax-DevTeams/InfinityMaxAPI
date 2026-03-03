package com.yuyuto.infinitymaxapi.api.libs;

import com.yuyuto.infinitymaxapi.api.libs.registry.ModRegistriesProvider;
import com.yuyuto.infinitymaxapi.api.libs.registry.settings.BlockSettings;

public final class ModBlocks {

    private ModBlocks() {}

    public static <T> T register(String id, T block, float strength, boolean noOcclusion) {
        BlockSettings settings = new BlockSettings();
        settings.strength = strength;
        settings.noOcclusion = noOcclusion;
        ModRegistriesProvider.get().registerBlock(id, block, settings);
        return block;
    }
}
