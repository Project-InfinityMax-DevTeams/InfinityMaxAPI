package com.yuyuto.infinitymaxapi.gamelibs.data;

import java.util.regex.Pattern;

/**
 * PIMX用キー定義クラス
 * 命名規則: modid.eventid.argument
 */
public final class PIMXKey {

    private static final Pattern KEY_PATTERN =
            Pattern.compile("^[a-z0-9_]+\\.[a-z0-9_]+\\.[a-z0-9_]+$");

    private final String key;

    public PIMXKey(String key) {
        if (key == null || !KEY_PATTERN.matcher(key).matches()) {
            throw new PIMXException("Invalid PIMX Key format: " + key);
        }
        this.key = key;
    }

    public String value() {
        return key;
    }

    @Override
    public String toString() {
        return key;
    }

    public static PIMXKey fromString(String str) {
        return new PIMXKey(str);
    }
}