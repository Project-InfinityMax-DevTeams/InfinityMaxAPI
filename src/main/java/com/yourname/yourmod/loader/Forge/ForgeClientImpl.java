package com.yourname.yourmod.loader.forge;

import com.yourname.yourmod.loader.LoaderExpectPlatform.Client;
import com.yourname.yourmod.client.ClientEntrypoint;

public final class ForgeClientImpl implements Client {

    @Override
    public void init() {
        ClientEntrypoint.init();
    }
}