package com.yuyuto.infinitymaxapi.api.client;

public final class ClientEntryPoint {

    private ClientEntryPoint(){}

    public static void init(){
        ClientEvents.register();
        ClientRenderers.register();
        ClientScreens.register();
        ClientInputs.register();
    }
}
