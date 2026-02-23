package com.yuyuto.infinitymaxapi.gamelibs.data;

/**
 * PIMX型定義
 */
public final class PIMXType<T> {

    private final Class<T> type;

    public PIMXType(Class<T> type) {
        if (type == null) {
            throw new PIMXException("Type cannot be null");
        }
        this.type = type;
    }

    public Class<T> getTypeClass() {
        return type;
    }

    public boolean isValid(Object value) {
        return type.isInstance(value);
    }
}