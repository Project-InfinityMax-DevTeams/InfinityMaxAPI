package com.yuyuto.infinitymaxapi.gamelibs.data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PIMXRegistry {

    private final Map<PIMXKey, PIMXData<?>> registry = new ConcurrentHashMap<>();

    public <T> void register(PIMXData<T> data) {
        if (registry.containsKey(data.key())) {
            throw new PIMXException("Duplicate key: " + data.key());
        }
        registry.put(data.key(), data);
    }

    @SuppressWarnings("unchecked")
    public <T> PIMXData<T> get(PIMXKey key) {
        return (PIMXData<T>) registry.get(key);
    }

    public boolean contains(PIMXKey key) {
        return registry.containsKey(key);
    }

    public void remove(PIMXKey key) {
        registry.remove(key);
    }
}