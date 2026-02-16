package com.yourname.yourmod.loader.forge;

import com.yourname.yourmod.YourMod;
import com.yourname.yourmod.loader.Platform;
import net.minecraftforge.fml.common.Mod;

@Mod("yourmod")
public final class ForgeEntrypoint {

    public ForgeEntrypoint() {
        Platform.set(new ForgePlatform());
        Platform.get().network().register();
        YourMod.init();
    }
}
