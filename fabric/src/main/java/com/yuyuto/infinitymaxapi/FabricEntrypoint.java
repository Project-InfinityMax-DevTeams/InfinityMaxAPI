package com.yuyuto.infinitymaxapi;

import com.yuyuto.infinitymaxapi.loader.Platform;
import net.fabricmc.api.ModInitializer;

public final class FabricEntrypoint implements ModInitializer {

    @Override
    public void onInitialize() {
        Platform.get().network().register();
        InfinityMaxAPI.init();
    }
}
