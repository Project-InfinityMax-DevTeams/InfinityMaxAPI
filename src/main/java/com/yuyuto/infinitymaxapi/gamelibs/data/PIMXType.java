package com.yuyuto.infinitymaxapi.gamelibs.data;

public interface PIMXType<T> {

    boolean isValid(Object value);

    String getId();

    Object serialize(T value);

    T deserialize(Object raw);

    T cast(Object value);
}