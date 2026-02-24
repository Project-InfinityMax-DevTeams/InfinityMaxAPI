package com.yuyuto.infinitymaxapi.gamelibs.data;

public interface PIMXRegistry {

    <T> void register(PIMXData<T> data);

    <T> void update(PIMXKey key, T newValue);

    <T> T getValue(PIMXKey key, PIMXType<T> type);

    void remove(PIMXKey key);

    void save();

    void load();
}