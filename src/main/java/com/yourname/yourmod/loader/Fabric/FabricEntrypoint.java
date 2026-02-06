package com.yourname.yourmod.loader.fabric;

import com.yourname.yourmod.YourMod;
import com.yourname.yourmod.loader.Platform;
import net.fabricmc.api.ModInitializer;

public class FabricEntrypoint implements ModInitializer {

    @Override
    public void onInitialize() {
        Platform.set(new FabricPlatform());
        YourMod.init();
    }
}