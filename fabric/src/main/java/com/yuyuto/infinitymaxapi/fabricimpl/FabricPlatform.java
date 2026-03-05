package com.yuyuto.infinitymaxapi.fabricimpl;

import com.yuyuto.infinitymaxapi.api.libs.ModRegistries;
import com.yuyuto.infinitymaxapi.loader.LoaderExpectPlatform;

public final class FabricPlatform implements LoaderExpectPlatform {

    private static final String MOD_ID = "infinitymaxapi";

    private final ModRegistries registries = new FabricRegistriesImpl();
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
    public com.yuyuto.infinitymaxapi.api.libs.ModRegistries registries() {
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
    public void commit() {
        registries.commit();
    }
}
