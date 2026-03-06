package com.yuyuto.infinitymaxapi;
import com.yuyuto.infinitymaxapi.InfinityMaxAPI;
import com.yuyuto.infinitymaxapi.loader.Platform;
import net.minecraftforge.fml.common.Mod;

@Mod("infinitymaxapi")
public final class ForgeEntrypoint {

    public ForgeEntrypoint() {
        Platform.set(new ForgePlatform());
        ModRegistriesProvider.set(Platform.get().registries());
        Platform.get().network().register();
        InfinityMaxAPI.init();
    }
}
