package com.yuyuto.infinitymaxapi.fabricimpl;

import com.yuyuto.infinitymaxapi.api.libs.datagen.DataGenSpec;
import com.yuyuto.infinitymaxapi.api.libs.datagen.runtime.DataGenExecutor;

/**
 * Fabric-side DataGen executor adapter.
 *
 * <p>MDK implementations should translate [DataGenSpec] into Fabric DataGeneratorEntrypoint providers.
 */
public final class FabricDslDataGenExecutor implements DataGenExecutor {

    @Override
    public void execute(DataGenSpec spec) {
        // Intentionally definition-only in this repository.
        // MDK Fabric module should replace this with Fabric data generator registration.
    }
}
