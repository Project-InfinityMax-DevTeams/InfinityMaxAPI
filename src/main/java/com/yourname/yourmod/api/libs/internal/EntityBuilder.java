package com.yourname.yourmod.api.libs.internal;

import com.yourname.yourmod.api.libs.ModRegistries;
import java.util.function.Supplier;

public final class EntityBuilder<T, C> {

    private final String id;
    private final Supplier<T> factory;
    private final  Supplier<C> category;
    private float width = 0.6f;
    private float height = 1.8f;

    public EntityBuilder(String id, Supplier<T> factory) {
        this.id = id;
        this.factory = factory;
    }

    public EntityBuilder<T, C> category(Supplier<C> category) {
        this.category = category;
        return this;
    }

    public EntityBuilder<T, C> size(float width, float height) {
        this.width = width;
        this.height = height; 
        return this;
    }

    public T build() {
        T entity = factory.get();
        ModRegistries.registerEntity(id, entity, category, width, height);
        return entity;
    }
}