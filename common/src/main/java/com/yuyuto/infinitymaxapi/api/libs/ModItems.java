package com.yuyuto.infinitymaxapi.api.libs;

public final class ModItems {

    private ModItems() {}

    public static <T> T register(String name, T item) {
        ModRegistries.registerItem(name, item);
        return item;
    }
}
