package com.yuyuto.infinitymaxapi.api.libs.datagen.runtime;

import com.yuyuto.infinitymaxapi.api.libs.datagen.DataGenSpec;

/**
 * Java abstraction for loader-specific DataGen execution.
 *
 * <p>Execution is not performed inside common directly; loader/MDK side should provide
 * an implementation that translates {`@link` DataGenSpec} into Forge/Fabric data providers.
 */
@FunctionalInterface
public interface DataGenExecutor {

    /**
     * Executes DataGen using a loader specific backend.
     *
     * @param spec immutable DataGen definitions
     */
    void execute(DataGenSpec spec);
}
