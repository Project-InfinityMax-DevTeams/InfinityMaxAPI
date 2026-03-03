package com.yuyuto.infinitymaxapi.loader.forge;

import com.yuyuto.infinitymaxapi.loader.LoaderExpectPlatform;

import java.util.HashMap;
import java.util.Map;

/**
 * Forge 向けの登録実体。
 *
 * <p>現段階では「DSLから渡された定義を受け取り、Loader内で登録管理する」責務に限定。
 * Forge API への最終バインドはこのクラスに集約する。</p>
 */
public final class ForgeRegistriesImpl implements LoaderExpectPlatform.Registries {

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
     * 指定した名前でアイテム定義を内部のアイテム登録マップに保存する。
     *
     * @param name 登録に使用する一意の名前（キー）
     * @param item 保存するアイテム定義オブジェクト
     */
    @Override
    public <T> void item(String name, T item) {
        items.put(name, item);
    }

    @Override
    public <T> void block(String name, T block, float strength, boolean noOcclusion) {
        blocks.put(name, block);
    }

    @Override
    public <T, C> void entity(String name, T entityType, C category, float width, float height) {
        entities.put(name, entityType);
    }

    /**
     * 指定した名前でブロックエンティティ型を登録する。
     *
     * @param name 登録に使用する識別名
     * @param blockEntityType 登録するブロックエンティティの型オブジェクト
     * @param blocks 関連するブロック（現在は格納されず無視される）
     */
    @Override
    public <T, B> void blockEntity(String name, T blockEntityType, B... blocks) {
        blockEntities.put(name, blockEntityType);
    }

    /**
     * 名前付きのデータ生成定義を内部レジストリに登録する。
     *
     * @param name 登録に使用する一意の識別名
     * @param dataGenDefinition 登録するデータ生成定義オブジェクト
     */
    @Override
    public <T> void dataGen(String name, T dataGenDefinition) {
        dataGens.put(name, dataGenDefinition);
    }

    /**
     * 指定した名前で GUI 定義を登録する。
     *
     * @param name 登録に使用する一意の識別名
     * @param guiDefinition 登録する GUI 定義オブジェクト
     */
    @Override
    public <T> void gui(String name, T guiDefinition) {
        guis.put(name, guiDefinition);
    }

    /**
     * 指定した名前でワールド定義を内部レジストリに保存する。
     *
     * @param name 登録に使用する一意の名前
     * @param worldDefinition 保存するワールド定義オブジェクト
     */
    @Override
    public <T> void world(String name, T worldDefinition) {
        worlds.put(name, worldDefinition);
    }

    /**
     * ネットワーク定義を内部レジストリに登録する。
     *
     * 指定された識別名でネットワーク定義を内部マップに格納する。
     *
     * @param name 登録キーとなる識別名
     * @param networkDefinition 格納するネットワーク定義オブジェクト
     */
    @Override
    public <T> void network(String name, T networkDefinition) {
        networks.put(name, networkDefinition);
    }

    /**
     * 指定された名前でパケット定義を内部レジストリに登録する。
     *
     * @param name 登録キーとなる識別文字列
     * @param packetDefinition 登録するパケット定義オブジェクト
     */
    @Override
    public <T> void packet(String name, T packetDefinition) {
        packets.put(name, packetDefinition);
    }
}
