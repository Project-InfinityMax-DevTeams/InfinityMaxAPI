package com.yuyuto.infinitymaxapi.loader.forge;

import com.yuyuto.infinitymaxapi.loader.LoaderExpectPlatform;
import com.yuyuto.infinitymaxapi.loader.forge.*;

public final class ForgePlatform implements LoaderExpectPlatform {

    private static final String MOD_ID = "infinitymaxapi";

    private final Registries registries = new ForgeRegistriesImpl();
    private final Network network = new ForgeNetworkImpl();
    private final Events events = new ForgeEventsImpl();

    @Override
    public String id(String path) {
        return MOD_ID + ":" + path;
    }

    @Override
    public boolean isClient() {
        return false;
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
}
