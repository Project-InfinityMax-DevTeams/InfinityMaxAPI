package com.yourname.yourmod.api.libs.internal;

import com.yourname.yourmod.api.libs.ModRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.function.Supplier;

public final class EntityBuilder<T extends Entity> {

    private final String id;
    private final EntityType.EntityFactory<T> factory;
    private MobCategory category = MobCategory.MISC;
    private float width = 0.6f;
    private float height = 1.8f;

    public EntityBuilder(String id, EntityType.EntityFactory<T> factory) {
        this.id = id;
        this.factory = factory;
    }

    public EntityBuilder<T> category(MobCategory category) {
        this.category = category;
        return this;
    }

    public EntityBuilder<T> size(float width, float height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public EntityType<T> build() {
        EntityType<T> type = EntityType.Builder
                .of(factory, category)
                .sized(width, height)
                .build(id);

        ModRegistries.registerEntity(id, type);
        return type;
    }
}