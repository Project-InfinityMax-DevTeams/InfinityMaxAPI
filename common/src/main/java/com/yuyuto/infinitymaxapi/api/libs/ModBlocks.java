package com.yuyuto.infinitymaxapi.api.libs;

public final class ModBlocks {

    private ModBlocks() {}

    public static <T> T register(String name, T block, float hardness, boolean isTransparent) {
        ModRegistries.registerBlock(name, block, hardness, isTransparent);
        return block;
    }
}
