package com.yuyuto.infinitymaxapi.api.libs.datagen.runtime;

import com.yuyuto.infinitymaxapi.api.libs.datagen.DataGenSpec;
import java.util.Objects;

/**
 * Shared Java-side abstraction bridge between Kotlin DSL definitions and loader execution.
 */
public final class DataGenBridge {

    private static DataGenDefinitionProvider provider = () -> new DataGenSpec(
            java.util.List.of(),
            java.util.List.of(),
            java.util.List.of(),
            java.util.List.of(),
            java.util.List.of(),
            java.util.List.of(),
            java.util.List.of(),
            java.util.List.of(),
            java.util.List.of(),
            java.util.List.of()
    );

    private DataGenBridge() {}

    /** Registers the MDK-side DSL definition provider. */
    public static void setProvider(DataGenDefinitionProvider value) {
        provider = value == null ? provider : value;
    }

    /** Returns the current DataGen definition graph. */
    public static DataGenSpec definitions() {
        return provider.provide();
    }

    /** Runs execution with the given loader executor. */
    public static void run(DataGenExecutor executor) {
        Objects.requireNonNull(executor, "executor").execute(definitions());
    }
}
