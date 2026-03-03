package com.yuyuto.infinitymaxapi.api.libs.registry;

import com.yuyuto.infinitymaxapi.api.libs.ModRegistries;

import java.util.Objects;

/**
 * 実行時に有効な ModRegistries 実装を保持するプロバイダ。
 *
 * <p>Forge/Fabric のエントリポイントで set し、DSL 実行時に get で利用する。</p>
 */
public final class ModRegistriesProvider {

    private static ModRegistries instance;

    private ModRegistriesProvider() {
    }

    public static void set(ModRegistries registries) {
        instance = Objects.requireNonNull(registries, "registries");
    }

    public static ModRegistries get() {
        if (instance == null) {
            throw new IllegalStateException("ModRegistries is not initialized. Loader must call ModRegistriesProvider.set(...)");
        }
        return instance;
    }
}
