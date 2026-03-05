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
        java.util.Objects.requireNonNull(spec, "spec");
        throw new UnsupportedOperationException(
                "ForgeDslDataGenExecutor is a placeholder. Wire GatherDataEvent providers in the MDK Forge module."
        );
    }
}
