package com.yuyuto.infinitymaxapi.api.registry;

import java.util.HashMap;
import java.util.Map;

public class RegistryDefinition {

    private final Map<String, BlockDefinition> blocks = new HashMap<>();
    private final Map<String, ItemDefinition> items = new HashMap<>();
    private final Map<String, EntityDefinition> entities = new HashMap<>();

    public Map<String, BlockDefinition> getBlocks() {
        return blocks;
    }

    public Map<String, ItemDefinition> getItems() {
        return items;
    }

    public Map<String, EntityDefinition> getEntities() {
        return entities;
    }

    public void addBlock(BlockDefinition def){
        blocks.put(def.getId(), def);
    }

    public void addItem(ItemDefinition def){
        items.put(def.getId(), def);
    }

    public void addEntity(EntityDefinition def){
        entities.put(def.getId(), def);
    }
}
