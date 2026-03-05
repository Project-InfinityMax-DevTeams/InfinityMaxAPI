package com.yuyuto.infinitymaxapi.forgeimpl;

import com.yuyuto.infinitymaxapi.api.libs.datagen.DataGenSpec;
import com.yuyuto.infinitymaxapi.api.libs.datagen.runtime.DataGenExecutor;

/**
 * Forge-side DataGen executor adapter.
 *
 * <p>MDK implementations should translate [DataGenSpec] into Forge GatherData providers.
 */
public final class ForgeDslDataGenExecutor implements DataGenExecutor {

    @Override
    public void execute(DataGenSpec spec) {
        // Intentionally definition-only in this repository.
        // MDK Forge module should replace this with GatherDataEvent provider wiring.
    }
}
