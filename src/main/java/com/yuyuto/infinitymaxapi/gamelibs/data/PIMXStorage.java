package com.yuyuto.infinitymaxapi.gamelibs.data;

import java.util.Map;

public interface PIMXStorage {

    Object raw = data.type().serialize(data.value());
    T value = type.deserialize(rawFromFile);

    void save(Map<PIMXKey, PIMXData<?>> data);

    Map<PIMXKey, PIMXData<?>> load();
}