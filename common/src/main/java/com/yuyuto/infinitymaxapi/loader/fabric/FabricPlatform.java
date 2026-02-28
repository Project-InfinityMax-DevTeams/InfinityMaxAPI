package com.yuyuto.infinitymaxapi.loader.fabric;

import com.yuyuto.infinitymaxapi.loader.LoaderExpectPlatform;

public final class FabricPlatform implements LoaderExpectPlatform {

    private static final String MOD_ID = "infinitymaxapi";

    private final Registries registries = new FabricRegistriesImpl();
    private final Network network = new FabricNetworkImpl();
    private final Events events = new FabricEventsImpl();

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
