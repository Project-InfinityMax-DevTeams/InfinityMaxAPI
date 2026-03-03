package com.yuyuto.infinitymaxapi.loader.fabric;

import com.yuyuto.infinitymaxapi.InfinityMaxAPI;
import com.yuyuto.infinitymaxapi.loader.Platform;
import com.yuyuto.infinitymaxapi.api.libs.registry.ModRegistriesProvider;
import net.fabricmc.api.ModInitializer;

public final class FabricEntrypoint implements ModInitializer {

    @Override
    public void onInitialize() {
        Platform.set(new FabricPlatform());
        ModRegistriesProvider.set(Platform.get().registries());
        Platform.get().network().register();
        InfinityMaxAPI.init();
    }
}
