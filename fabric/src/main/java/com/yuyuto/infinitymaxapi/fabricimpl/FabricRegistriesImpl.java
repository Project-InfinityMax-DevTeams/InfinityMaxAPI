package com.yuyuto.infinitymaxapi.fabricimpl;

import com.yuyuto.infinitymaxapi.api.libs.ModRegistries;
import com.yuyuto.infinitymaxapi.api.libs.registry.settings.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Fabric 向けの登録実体。
 *
 * <p>DSL で定義された Settings と template を保持し、最終的な Fabric API 登録に橋渡しする。</p>
 */
public final class FabricRegistriesImpl implements ModRegistries {
    private record Entry<T, S>(T template, S settings) {} 
    private record BlockEntityEntry<T, B>(T template, B[] blocks, BlockEntitySettings settings) {}

    private final Map<String, Entry<?, ItemSettings>> items = new HashMap<>();  
    private final Map<String, Entry<?, BlockSettings>> blocks = new HashMap<>();  
    private final Map<String, Entry<?, EntitySettings<?>>> entities = new HashMap<>();  
    private final Map<String, BlockEntityEntry<?, ?>> blockEntities = new HashMap<>();  
    private final Map<String, Entry<?, DataGenSettings>> dataGens = new HashMap<>();  
    private final Map<String, Entry<?, GuiSettings>> guis = new HashMap<>();  
    private final Map<String, Entry<?, WorldSettings>> worlds = new HashMap<>();  
    private final Map<String, Entry<?, NetworkSettings>> networks = new HashMap<>();  
    private final Map<String, Entry<?, PacketSettings>> packets = new HashMap<>();

    @Override
    public <T> void registerItem(String id, T template, ItemSettings settings) { putUnique(items, id, new Entry<>(template, settings)); }

    @Override
    public <T> void registerBlock(String id, T template, BlockSettings settings) { putUnique(blocks, id, new Entry<>(template, settings)); }  

    @Override
    public <T, C> void registerEntity(String id, T template, EntitySettings<C> settings) { putUnique(entities, id, new Entry<>(template, settings)); }

    @Override
    public <T, B> void registerBlockEntity(String id, T template, B[] blocks, BlockEntitySettings settings) {  
        B[] blocksCopy = blocks == null ? null : blocks.clone();
        putUnique(blockEntities, id, new BlockEntityEntry<>(template, blocksCopy, settings));  
    }

    @Override
    public <T> void registerDataGen(String id, T template, DataGenSettings settings) { putUnique(dataGens, id, new Entry<>(template, settings)); }  

    @Override
    public <T> void registerPacket(String id, T template, PacketSettings settings) { putUnique(packets, id, new Entry<>(template, settings)); }

    @Override
    public <T> void registerNetwork(String id, T template, NetworkSettings settings) { putUnique(networks, id, new Entry<>(template, settings)); }

    @Override
    public <T> void registerGui(String id, T template, GuiSettings settings) { putUnique(guis, id, new Entry<>(template, settings)); }

    @Override
    public <T> void registerWorld(String id, T template, WorldSettings settings) { putUnique(worlds, id, new Entry<>(template, settings)); }
    private static <V> void putUnique(Map<String, V> map, String id, V value) {
        Objects.requireNonNull(id, "registry id must not be null");
        if (id.isBlank()) {
            throw new IllegalArgumentException("registry id must not be blank");
        }
        if (map.putIfAbsent(id, value) != null) {
            throw new IllegalStateException("Duplicate registry id: " + id);
        }
    }

}
