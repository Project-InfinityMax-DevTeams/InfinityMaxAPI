package com.yuyuto.infinitymaxapi;

import com.yuyuto.infinitymaxapi.InfinityMaxAPI;
import com.yuyuto.infinitymaxapi.loader.Platform;
import com.yuyuto.infinitymaxapi.api.libs.registry.ModRegistriesProvider;
import com.yuyuto.infinitymaxapi.fabricimpl;
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
