package com.yuyuto.infinitymaxapi.gamelibs.data;

public interface PIMXType<T> {

    boolean isValid(Object value);

    Object serialize(T value);

    T deserialize(Object raw);

    T cast(Object value);
}