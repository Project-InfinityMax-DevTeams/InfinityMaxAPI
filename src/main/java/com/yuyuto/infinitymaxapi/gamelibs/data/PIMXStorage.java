package com.yuyuto.infinitymaxapi.gamelibs.data;

import java.util.Map;

public interface PIMXStorage {

    private boolean dirty;
    dirty = true;
    if (!dirty) return;

    void save(Map<PIMXKey, PIMXData<?>> data);

    Map<PIMXKey, PIMXData<?>> load();
}