package com.yourname.yourmod.api.libs.internal;

import com.yourname.yourmod.api.libs.ModRegistries;
import java.util.function.Supplier;

public final class EntityBuilder<T> {

    private final String id;
    private final Supplier<T> factory;
    private Object category;
    private float width = 0.6f;  // デフォルト幅
    private float height = 1.8f; // デフォルト高さ

    public EntityBuilder(String id, Supplier<T> factory) {
        this.id = id;
        this.factory = factory;
    }

    public EntityBuilder<T> category(Object category) {
        this.category = category;
        return this;
    }

    public EntityBuilder<T> size(float width, float height) {
        this.width = width;
        this.height = height
        return this;
    }

    public T build() {
        T entity = factory.get();
        ModRegistries.registerEntity(id, entity, category, width, height);
        return entity;
    }
}
