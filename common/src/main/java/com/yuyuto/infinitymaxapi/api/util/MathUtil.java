package com.yuyuto.infinitymaxapi.api.util;

/**
 * 謨ｰ蟄ｦ邉ｻ繝ｦ繝ｼ繝・ぅ繝ｪ繝・ぅ
 */
public final class MathUtil {

    private MathUtil() {}

    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}
