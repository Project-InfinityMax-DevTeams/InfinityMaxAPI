package com.yourname.yourmod.api.client;

public final class ClientEntrypoint {

    private ClientEntrypoint() {}

    public static void init() {
        ClientEvents.register();
        ClientRenderers.register();
        ClientScreens.register();
        ClientInputs.register();
    }
}