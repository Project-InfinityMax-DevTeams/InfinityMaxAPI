package com.yuyuto.infinitymaxapi.api.registry;

import java.util.HashMap;
import java.util.Map;

public class RegistryDefinition {

    private final Map<String, BlockDefinition> blocks = new HashMap<>();
    private final Map<String, ItemDefinition> items = new HashMap<>();

    public Map<String, BlockDefinition> getBlocks() {
        return blocks;
    }

    public Map<String, ItemDefinition> getItems() {
        return items;
    }

    public void addBlock(BlockDefinition def){
        blocks.put(def.getId(), def);
    }

    public void addItem(ItemDefinition def){
        items.put(def.getId(), def);
    }
}
