package com.yourname.yourmod.api.libs;

import java.util.function.Supplier;

public final class ModBlocks {

    private ModBlocks() {}

    public static <T> T register(String name, Supplier<T> block, float hardness, boolean isTransparent) {
        T value = block.get();
        ModRegistries.registerBlock(name, value hardness isTransparent);
        return value;
    }
}
