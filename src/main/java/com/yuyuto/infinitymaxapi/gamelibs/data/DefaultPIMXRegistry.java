package com.yuyuto.infinitymaxapi.gamelibs.data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultPIMXRegistry implements PIMXRegistry {

    private final Map<PIMXKey, PIMXData<?>> registry = new ConcurrentHashMap<>();
    private final PIMXStorage storage;

    private boolean dirty = false;

    public DefaultPIMXRegistry(PIMXStorage storage) {
        this.storage = storage;
    }

    @Override
    public <T> void register(PIMXData<T> data) {
        registry.put(data.key(), data);
        dirty = true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> void update(PIMXKey key, T newValue) {

        PIMXData<?> oldRaw = registry.get(key);

        if (oldRaw == null) {
            throw new PIMXException("Key not found: " + key);
        }

        PIMXData<T> old = (PIMXData<T>) oldRaw;

        if (!old.type().isValid(newValue)) {
            throw new PIMXException("Invalid value");
        }

        PIMXData<T> updated =
                new PIMXData<>(
                        key,
                        old.type(),
                        old.scope(),
                        old.syncPolicy(),
                        newValue
                );

        registry.put(key, updated);
        dirty = true;
    }
    @Override
    public void save() {

        if (!dirty) return;

        storage.save(registry);
        dirty = false;
    }
}