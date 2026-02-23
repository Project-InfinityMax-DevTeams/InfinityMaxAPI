package com.yuyuto.infinitymaxapi.gamelibs.data;

/**
 * PIMXデータ本体
 * 不変オブジェクトとして設計
 */
public record PIMXData<T>(PIMXKey key, PIMXType<T> type, PIMXScope scope, PIMXSyncPolicy syncPolicy, T value) {

    public PIMXData {

        if (key == null) {
            throw new PIMXException("Key cannot be null");
        }

        if (type == null) {
            throw new PIMXException("Type cannot be null");
        }

        if (!type.isValid(value)) {
            throw new PIMXException("Value does not match declared type");
        }

    }
}