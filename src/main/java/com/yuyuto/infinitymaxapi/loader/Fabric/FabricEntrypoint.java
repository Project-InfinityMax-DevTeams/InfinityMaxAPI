package com.yuyuto.infinitymaxapi.loader.fabric;

import com.yuyuto.infinitymaxapi.infinitymaxapi;
import com.yuyuto.infinitymaxapi.loader.Platform;
import net.fabricmc.api.ModInitializer;

public final class FabricEntrypoint implements ModInitializer {

    @Override
    public void onInitialize() {
        Platform.set(new FabricPlatform());
        Platform.get().network().register();
        InfinityMaxAPI.init();
    }
}
