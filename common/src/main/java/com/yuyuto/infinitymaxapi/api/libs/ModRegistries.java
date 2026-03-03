package com.yuyuto.infinitymaxapi.api.libs;

import com.yuyuto.infinitymaxapi.loader.LoaderExpectPlatform;
import com.yuyuto.infinitymaxapi.loader.Platform;

/**
 * 登録 DSL から Loader 実装へ橋渡しするレジストリ集約クラス。
 *
 * <p>Core 側は Loader 具体型に依存せず、Platform 抽象のみを参照する。</p>
 */
public final class ModRegistries {

    private ModRegistries() {
    }

    private static LoaderExpectPlatform.Registries registries() {
        return Platform.get().registries();
    }

    public static <T> void registerItem(String id, T item) {
        registries().item(id, item);
    }

    public static <T> void registerBlock(String id, T block, float strength, boolean noOcclusion) {
        registries().block(id, block, strength, noOcclusion);
    }

    public static <T, C> void registerEntity(String id, T entityType, C category, float width, float height) {
        registries().entity(id, entityType, category, width, height);
    }

    public static <T, B> void registerBlockEntity(String id, T blockEntityType, B... blocks) {
        registries().blockEntity(id, blockEntityType, blocks);
    }
}
