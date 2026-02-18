package com.yourname.yourmod.api.libs;

import java.util.function.Supplier;

public final class ModEntities {

    private ModEntities() {}

    public static <T,C> T register(String name, Supplier<T> entity,C category,float width,float height) {
        T value = entity.get();
        ModRegistries.registerEntity(name, value,category,width,height);
        return value;
    }
}
