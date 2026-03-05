package com.yuyuto.infinitymaxapi.api.libs.datagen.runtime;

import com.yuyuto.infinitymaxapi.api.libs.datagen.DataGenSpec;

/**
 * Java abstraction that provides Kotlin DSL-built DataGen definitions.
 *
 * <p>MDK projects are expected to implement this interface and return [DataGenSpec]
 * instances created via the Kotlin DSL API.
 */
@FunctionalInterface
public interface DataGenDefinitionProvider {

    /**
     * Returns the immutable DataGen definition graph.
     *
     * @return DataGen specification produced by Kotlin DSL
     */
    DataGenSpec provide();
}
