package com.yuyuto.infinitymaxapi.gamelibs.data;

public interface PIMXMergeHandler<T> {
    T merge(T oldVal, T newVal);
}