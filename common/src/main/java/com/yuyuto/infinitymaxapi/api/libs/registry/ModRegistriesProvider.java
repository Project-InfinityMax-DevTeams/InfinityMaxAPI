package com.yuyuto.infinitymaxapi.api.libs.registry;

import com.yuyuto.infinitymaxapi.api.libs.ModRegistries;

import java.util.Objects;

/**
 * 実行時に有効な ModRegistries 実装を保持するプロバイダ。
 *
 * <p>Forge/Fabric のエントリポイントで set し、DSL 実行時に get で利用する。</p>
 */
public final class ModRegistriesProvider {

    private static volatile ModRegistries instance;

    private ModRegistriesProvider() {
    }

    public static synchronized void set(ModRegistries registries) {
        Objects.requireNonNull(registries, "registries");
        if (instance != null) {
            throw new IllegalStateException("ModRegistries is already initialized.");
        }
        instance = registries;
    }

    public static ModRegistries get() {
        final ModRegistries registries = instance;
        if (registries == null) {
            throw new IllegalStateException("ModRegistries is not initialized. Loader must call ModRegistriesProvider.set(...)");
        }
        return registries;
    }
}
