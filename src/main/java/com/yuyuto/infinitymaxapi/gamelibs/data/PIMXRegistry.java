package com.yuyuto.infinitymaxapi.gamelibs.data;

public interface PIMXRegistry {

    <T> void register(PIMXData<T> data);

    <T> T getValue(PIMXKey key, PIMXType<T> type);

    void update(PIMXKey key, Object newValue);

    void remove(PIMXKey key);

    void save();   // 永続化
    void load();   // 読み込み
}