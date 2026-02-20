package com.yuyuto.infinitymaxapi.loader.forge;

import com.yuyuto.infinitymaxapi.infinitymaxapi;
import com.yuyuto.infinitymaxapi.loader.Platform;
import net.minecraftforge.fml.common.Mod;

@Mod("infinitymaxapi")
public final class ForgeEntrypoint {

    public ForgeEntrypoint() {
        Platform.set(new ForgePlatform());
        Platform.get().network().register();
        InfinityMaxAPI.init();
    }
}
