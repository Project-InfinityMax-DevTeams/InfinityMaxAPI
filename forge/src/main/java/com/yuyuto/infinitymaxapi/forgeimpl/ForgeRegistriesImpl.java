package com.yuyuto.infinitymaxapi.forgeimpl;

import com.yuyuto.infinitymaxapi.api.libs.ModRegistries;
import com.yuyuto.infinitymaxapi.api.libs.registry.settings.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Forge 向けの登録実体。
 *
 * <p>DSL で定義された Settings と template を保持し、最終的な Forge API 登録に橋渡しする。</p>
 */
public final class ForgeRegistriesImpl implements ModRegistries {

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
    public <T> void registerItem(String id, T template, ItemSettings settings) { items.put(id, template); }

    @Override
    public <T> void registerBlock(String id, T template, BlockSettings settings) { blocks.put(id, template); }

    @Override
    public <T, C> void registerEntity(String id, T template, EntitySettings<C> settings) { entities.put(id, template); }

    @Override
    public <T, B> void registerBlockEntity(String id, T template, B[] blocks, BlockEntitySettings settings) { blockEntities.put(id, template); }

    @Override
    public <T> void registerDataGen(String id, T template, DataGenSettings settings) { dataGens.put(id, template); }

    @Override
    public <T> void registerPacket(String id, T template, PacketSettings settings) { packets.put(id, template); }

    @Override
    public <T> void registerNetwork(String id, T template, NetworkSettings settings) { networks.put(id, template); }

    @Override
    public <T> void registerGui(String id, T template, GuiSettings settings) { guis.put(id, template); }

    @Override
    public <T> void registerWorld(String id, T template, WorldSettings settings) { worlds.put(id, template); }
}
