package com.yourname.yourmod.api.libs.internal;

import com.yourname.yourmod.api.libs.handle.BlockHandle;

public final class BlockBuilder {

    private final String id;
    private Object template;
    private float strength = 1.0f;
    private boolean noOcclusion = false;

    public BlockBuilder(String id) {
        this.id = id;
    }

    public BlockBuilder template(Object template) {
        this.template = template;
        return this;
    }

    public BlockBuilder strength(float value) {
        this.strength = value;
        return this;
    }

    public BlockBuilder noOcclusion() {
        this.noOcclusion = true;
        return this;
    }

    public BlockHandle build() {
        Object block = template != null ? template : new Object();

        ModRegistries.registerBlock(
                id,
                block,
                strength,
                noOcclusion
        );

        return new BlockHandle(block);
    }
}
