package com.yuyuto.infinitymaxapi;

import net.fabricmc.api.ModInitializer;

public final class FabricEntrypoint implements ModInitializer {

    @Override
    public void onInitialize() {
        InfinityMaxAPI.init();
    }
}
