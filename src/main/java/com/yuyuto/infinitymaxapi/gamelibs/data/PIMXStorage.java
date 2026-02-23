package com.yuyuto.infinitymaxapi.gamelibs.data;

import java.util.Map;

public interface PIMXStorage {

    void save(Map<PIMXKey, PIMXData<?>> data);

    Map<PIMXKey, PIMXData<?>> load();
}