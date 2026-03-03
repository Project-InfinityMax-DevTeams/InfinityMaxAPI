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

    @Override
    public <T, B> void blockEntity(String name, T blockEntityType, B... blocks) {
        blockEntities.put(name, blockEntityType);
    }

    private static <T> void putUnique(Map<String, Object> map, String name, T value, String kind) {
        Object prev = map.putIfAbsent(name, value);
        if (prev != null) {
            throw new IllegalStateException(kind + " already registered: " + name);
        }
    @Override
    public <T> void dataGen(String name, T dataGenDefinition) {
        putUnique(dataGens, name, dataGenDefinition, "dataGen");
    }

    @Override
    public <T> void gui(String name, T guiDefinition) {
        putUnique(guis, name, guiDefinition, "gui");
    }

    @Override
    public <T> void world(String name, T worldDefinition) {
        putUnique(worlds, name, worldDefinition, "world");
    }

    @Override
    public <T> void network(String name, T networkDefinition) {
        putUnique(networks, name, networkDefinition, "network");
    }

    @Override
    public <T> void packet(String name, T packetDefinition) {
        putUnique(packets, name, packetDefinition, "packet");
    }
}
