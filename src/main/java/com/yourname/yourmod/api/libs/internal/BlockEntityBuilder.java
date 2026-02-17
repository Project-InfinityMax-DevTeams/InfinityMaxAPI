package com.yourname.yourmod.api.libs.internal;

import com.yourname.yourmod.api.libs.ModRegistries;
import java.util.function.Supplier;

public final class BlockEntityBuilder<T, B> {

    private final String id;
    private final Supplier<T> factory;
    private B[] blocks;

    public BlockEntityBuilder(String id, Supplier<T> factory) {
        this.id = id;
        this.factory = factory;
    }

    public final BlockEntityBuilder<T, B> blocks(B... blocks) {
        this.blocks = blocks;
        return this;
    }

    public T build() {
        T blockEntity = factory.get();
        ModRegistries.registerBlockEntity(id, blockEntity, blocks);
        return blockEntity;
    }
}