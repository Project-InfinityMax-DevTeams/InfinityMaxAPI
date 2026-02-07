package com.yourname.yourmod.loader.forge;

import com.yourname.yourmod.loader.LoaderExpectPlatform;
import com.yourname.yourmod.loader.LoaderExpectPlatform.Client;
import com.yourname.yourmod.api.datagen.DataGen;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import com.yourname.yourmod.api.lifecycle.LifecycleRegistry;
import com.yourname.yourmod.api.lifecycle.ModLifecycle;

public final class ForgePlatform implements LoaderExpectPlatform {

    private static final String MOD_ID = "yourmodid";

    // ランタイム
    private final Registries registries = new ForgeRegistriesImpl();
    private final Network network = new ForgeNetworkImpl();
    private final Events events = new ForgeEventsImpl();

    // Client（Dist判定で切替）
    private final Client client =
            FMLEnvironment.dist == Dist.CLIENT
                    ? new ForgeClientImpl()
                    : Client.noop();

    // DataGen
    private final DataGen dataGen = new ForgeDataGenImpl();

    @Override
    public ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    @Override
    public boolean isClient() {
        return FMLEnvironment.dist == Dist.CLIENT;
    }

    @Override
    public Registries registries() {
        return registries;
    }

    @Override
    public Network network() {
        return network;
    }

    @Override
    public Events events() {
        return events;
    }

    @Override
    public void fireLifecycle(ModLifecycle stage) {
        LifecycleRegistry.fire(stage);
    }

    @Override
    public Client client() {
        return client;
    }

    @Override
    public DataGen dataGen() {
        return dataGen;
    }
}