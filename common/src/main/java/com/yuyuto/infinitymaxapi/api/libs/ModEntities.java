package com.yuyuto.infinitymaxapi.api.libs;

import com.yuyuto.infinitymaxapi.api.libs.registry.ModRegistriesProvider;
import com.yuyuto.infinitymaxapi.api.libs.registry.settings.EntitySettings;

public final class ModEntities {

    private ModEntities() {}

    public static <T, C> T register(String id, T entity, C category, float width, float height) {
        EntitySettings<C> settings = new EntitySettings<>(category);
        settings.category = category;
        settings.width = width;
        settings.height = height;
        ModRegistriesProvider.get().registerEntity(id, entity, settings);
        return entity;
    }
}
