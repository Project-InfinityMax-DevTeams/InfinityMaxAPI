package com.yuyuto.infinitymaxapi.api.libs;

import com.yuyuto.infinitymaxapi.api.libs.registry.ModRegistriesProvider;
import com.yuyuto.infinitymaxapi.api.libs.registry.settings.ItemSettings;

public final class ModItems {

    private ModItems() {}

    public static <T> T register(String id, T item) {
        ModRegistriesProvider.get().registerItem(id, item, new ItemSettings());
        return item;
    }
}
