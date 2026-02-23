package com.yuyuto.infinitymaxapi.gamelibs.data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultPIMXRegistry implements PIMXRegistry {

    private final Map<PIMXKey, PIMXData<?>> registry = new ConcurrentHashMap<>();
    private final PIMXStorage storage;

    public DefaultPIMXRegistry(PIMXStorage storage) {
        this.storage = storage;
    }

    @Override
    public <T> void register(PIMXData<T> data) {
        if (registry.containsKey(data.key())) {
            throw new PIMXException("Duplicate key: " + data.key());
        }
        registry.put(data.key(), data);
    }

    @Override
    public <T> T getValue(PIMXKey key, PIMXType<T> type) {

        PIMXData<?> data = registry.get(key);

        if (data == null) {
            throw new PIMXException("Key not found: " + key);
        }

        if (!data.type().equals(type)) {
            throw new PIMXException("Type mismatch for key: " + key);
        }

        return type.cast(data.value());
    }

    @Override
    public void update(PIMXKey key, Object newValue) {

        PIMXData<?> old = registry.get(key);

        if (old == null) {
            throw new PIMXException("Key not found: " + key);
        }

        if (!old.type().isValid(newValue)) {
            throw new PIMXException("Invalid value for key: " + key);
        }

        PIMXData<?> updated = new PIMXData<>(
                key,
                old.type(),
                old.scope(),
                old.syncPolicy(),
                newValue
        );

        registry.put(key, updated);
    }

    @Override
    public void save() {
        storage.save(registry);
    }

    @Override
    public void load() {
        registry.clear();
        registry.putAll(storage.load());
    }

    @Override
    public void remove(PIMXKey key) {
        registry.remove(key);
    }
}