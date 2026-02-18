package com.yourname.yourmod.loader.fabric;

import com.yourname.yourmod.loader.LoaderExpectPlatform;
import java.util.HashMap;
import java.util.Map;

public final class FabricRegistriesImpl implements LoaderExpectPlatform.Registries {

    private final Map<String, Object> items = new HashMap<>();
    private final Map<String, Object> blocks = new HashMap<>();
    private final Map<String, Object> entities = new HashMap<>();
    private final Map<String, Object> blockEntities = new HashMap<>();

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
}