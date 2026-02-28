package com.yuyuto.infinitymaxapi.api.util;

/**
 * MOD ID陬懷勧
 */
public final class IdUtil {

    private static final String MODID = "infinitymaxapi";

    public static String id(String path) {
        return MODID + ":" + path;
    }
}
