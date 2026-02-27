package com.yuyuto.infinitymaxapi.api.libs;

public final class ModEntities {

    private ModEntities() {}

    public static <T, C> T register(String name, T entity, C category, float width, float height) {
        ModRegistries.registerEntity(name, entity, category, width, height);
        return entity;
    }
}
