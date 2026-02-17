package com.yourname.yourmod.api.libs.internal;

import com.yourname.yourmod.api.libs.ModRegistries;

public final class BlockBuilder<T> {

    private final String id;
    private T template;
    private float strength = 1.0f;
    private boolean noOcclusion = false;

    public BlockBuilder(String id) {
        this.id = id;
    }

    public BlockBuilder<T> template(T template) {
        this.template = template;
        return this;
    }

    public BlockBuilder<T> strength(float value) {
        this.strength = value;
        return this;
    }

    public BlockBuilder<T> noOcclusion() {
        this.noOcclusion = true;
        return this;
    }

    public T build() {
        if (template == null) {
            throw new IllegalStateException("Template must be provided");
        }

        ModRegistries.registerBlock(
                id,
                template,
                strength,
                noOcclusion
        );

        return template;
    }
}