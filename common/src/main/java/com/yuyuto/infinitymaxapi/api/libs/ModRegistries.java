package com.yuyuto.infinitymaxapi.api.libs;

import com.yuyuto.infinitymaxapi.loader.LoaderExpectPlatform;
import com.yuyuto.infinitymaxapi.loader.Platform;

/**
 * 登録 DSL から Loader 実装へ橋渡しするレジストリ集約クラス。
 *
 * <p>Core 側は Loader 具体型に依存せず、Platform 抽象のみを参照する。</p>
 */
public final class ModRegistries {

    /**
     * インスタンス化を禁止するユーティリティ用のプライベートデフォルトコンストラクタ。
     */
    private ModRegistries() {
    }

    /**
     * 現在のプラットフォーム実装から登録レジストリへのアクセスを取得する。
     *
     * @return 現在のプラットフォームが提供する {@link com.yuyuto.infinitymaxapi.api.platform.LoaderExpectPlatform.Registries} のインスタンス
     */
    private static LoaderExpectPlatform.Registries registries() {
        return Platform.get().registries();
    }

    /**
     * 指定した識別子でアイテムを中央の登録システムに登録する。
     *
     * @param id   登録に使用する識別子（例: "modid:name" の形式を想定）
     * @param item 登録するアイテムオブジェクト
     */
    public static <T> void registerItem(String id, T item) {
        registries().item(id, item);
    }

    /**
     * 指定したIDでブロックを登録する。
     *
     * @param id 登録に使用する識別子（名前空間を含む文字列）
     * @param block 登録するブロックオブジェクト
     * @param strength ブロックの破壊に必要な強さ（硬度／耐久性）
     * @param noOcclusion true の場合、視界や光の遮蔽を行わない（遮蔽物として扱われない）
     */
    public static <T> void registerBlock(String id, T block, float strength, boolean noOcclusion) {
        registries().block(id, block, strength, noOcclusion);
    }

    /**
     * 指定した識別子でエンティティタイプをレジストリに登録する。
     *
     * @param id 登録に使用する一意の識別子
     * @param entityType 登録するエンティティタイプのオブジェクト
     * @param category エンティティの分類（カテゴリ）
     * @param width エンティティの幅（ワールド単位、通常はブロック単位）
     * @param height エンティティの高さ（ワールド単位、通常はブロック単位）
     */
    public static <T, C> void registerEntity(String id, T entityType, C category, float width, float height) {
        registries().entity(id, entityType, category, width, height);
    }

    /**
     * ブロックエンティティタイプを指定したブロックと関連付けて登録する。
     *
     * @param id 登録に使用する識別子（ID）
     * @param blockEntityType 登録するブロックエンティティの型
     * @param blocks そのブロックエンティティに紐づけるブロック群
     */
    public static <T, B> void registerBlockEntity(String id, T blockEntityType, B... blocks) {
        registries().blockEntity(id, blockEntityType, blocks);
    }
}
