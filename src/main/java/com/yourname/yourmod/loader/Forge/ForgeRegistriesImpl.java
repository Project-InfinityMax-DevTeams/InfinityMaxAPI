package com.yourname.yourmod.loader.forge;

import com.yourname.yourmod.loader.LoaderExpectPlatform;
import java.util.HashMap;
import java.util.Map;

public final class ForgeRegistriesImpl implements LoaderExpectPlatform.Registries {

    private final Map<String, Object> items = new HashMap<>();
    private final Map<String, Object> blocks = new HashMap<>();
    private final Map<String, Object> entities = new HashMap<>();
    private final Map<String, Object> blockEntities = new HashMap<>();

    @Override
    public <T> void item(String name, T item) {
        items.put(name, item);
    }

    @Override
    public <T> void block(String name, T block) {
        blocks.put(name, block);
    }

    @Override
    public <T> void entity(String name, T entityType) {
        entities.put(name, entityType);
    }

    @Override
    public <T> void blockEntity(String name, T blockEntityType) {
        blockEntities.put(name, blockEntityType);
    }
}
