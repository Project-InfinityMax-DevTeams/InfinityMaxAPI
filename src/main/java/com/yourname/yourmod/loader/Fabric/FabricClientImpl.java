package com.yourname.yourmod.loader.fabric;

import com.yourname.yourmod.loader.LoaderExpectPlatform.Client;
import com.yourname.yourmod.client.ClientEntrypoint;

public final class FabricClientImpl implements Client {

    @Override
    public void init() {
        ClientEntrypoint.init();
    }
}