package com.yuyuto.infinitymaxapi.loader.fabric;

import com.yuyuto.infinitymaxapi.loader.LoaderExpectPlatform;

import java.util.HashMap;
import java.util.Map;

/**
 * Fabric 向けの登録実体。
 *
 * <p>現段階では「DSLから渡された定義を受け取り、Loader内で登録管理する」責務に限定。
 * Fabric API への最終バインドはこのクラスに集約する。</p>
 */
public final class FabricRegistriesImpl implements LoaderExpectPlatform.Registries {

    private final Map<String, Object> items = new HashMap<>();
    private final Map<String, Object> blocks = new HashMap<>();
    private final Map<String, Object> entities = new HashMap<>();
    private final Map<String, Object> blockEntities = new HashMap<>();
    private final Map<String, Object> dataGens = new HashMap<>();
    private final Map<String, Object> guis = new HashMap<>();
    private final Map<String, Object> worlds = new HashMap<>();
    private final Map<String, Object> networks = new HashMap<>();
    private final Map<String, Object> packets = new HashMap<>();

    /**
     * 指定した名前で内部のアイテム登録マップにアイテム定義を登録します。
     *
     * @param name 登録に使用する一意の名前
     * @param item 登録するアイテムの定義オブジェクト
     */
    @Override
    public <T> void item(String name, T item) {
        items.put(name, item);
    }

    /**
     * 指定した名前でブロック定義を内部のブロックレジストリに登録する。
     *
     * 指定された定義は内部のマップに保存され、後でバインド処理で使用される。
     *
     * @param name 登録キーとなる名前
     * @param block 登録するブロック定義オブジェクト
     * @param strength ブロックの強度を示すパラメータ（本実装では保存・使用されない）
     * @param noOcclusion ブロックの遮蔽設定を示すパラメータ（本実装では保存・使用されない）
     */
    @Override
    public <T> void block(String name, T block, float strength, boolean noOcclusion) {
        blocks.put(name, block);
    }

    @Override
    public <T, C> void entity(String name, T entityType, C category, float width, float height) {
        entities.put(name, entityType);
    }

    /**
     * ブロックエンティティの型定義を内部登録マップに格納する。
     *
     * @param name 登録に使用する識別名
     * @param blockEntityType 登録するブロックエンティティの型定義
     * @param blocks 関連するブロック（可変長引数）。この実装では格納処理に使用されない
     */
    @Override
    public <T, B> void blockEntity(String name, T blockEntityType, B... blocks) {
        blockEntities.put(name, blockEntityType);
    }

    /**
     * データ生成定義を内部のデータジェネレーター登録マップに登録する。
     *
     * @param name 登録に使用する一意の識別名
     * @param dataGenDefinition 登録するデータ生成（data generator）定義オブジェクト
     */
    @Override
    public <T> void dataGen(String name, T dataGenDefinition) {
        dataGens.put(name, dataGenDefinition);
    }

    /**
     * DSLから渡されたGUI定義を内部レジストリに登録する。
     *
     * @param name 登録するGUIの識別名（キー）
     * @param guiDefinition 登録するGUI定義オブジェクト
     */
    @Override
    public <T> void gui(String name, T guiDefinition) {
        guis.put(name, guiDefinition);
    }

    /**
     * ワールド定義を内部レジストリに登録する。
     *
     * 指定した名前で渡されたワールド定義を内部のマップに保存する。
     *
     * @param name 登録キーとなる名前
     * @param worldDefinition 保存するワールド定義オブジェクト
     */
    @Override
    public <T> void world(String name, T worldDefinition) {
        worlds.put(name, worldDefinition);
    }

    /**
     * 指定した名前でネットワーク定義を登録する。
     *
     * 指定した名前をキーとして与えられたネットワーク定義を内部のネットワーク登録領域に格納する。
     *
     * @param name 登録に使用する一意の名前
     * @param networkDefinition 登録するネットワーク定義オブジェクト
     */
    @Override
    public <T> void network(String name, T networkDefinition) {
        networks.put(name, networkDefinition);
    }

    /**
     * 指定された名称でパケット定義を内部レジストリに登録する。
     *
     * @param name 登録に使用する一意の名前
     * @param packetDefinition 登録するパケット定義オブジェクト
     */
    @Override
    public <T> void packet(String name, T packetDefinition) {
        packets.put(name, packetDefinition);
    }
}
