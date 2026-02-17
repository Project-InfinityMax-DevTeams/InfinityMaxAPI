package com.yourname.yourmod.api.libs;

import java.util.function.Supplier;

public final class ModBlockEntities {

    private ModBlockEntities() {}

    public static <T, B> T register(String name, Supplier<T> blockEntity, B... blocks) {
        T value = blockEntity.get();
        ModRegistries.registerBlockEntity(name, value, blocks);
        return value;
    }
}