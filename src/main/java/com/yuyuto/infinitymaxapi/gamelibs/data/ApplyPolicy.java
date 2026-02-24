package com.yuyuto.infinitymaxapi.gamelibs.data;

/**
 * 反映タイミングポリシー
 */
public enum ApplyPolicy {
    IMMEDIATE,   // 即時反映
    NEXT_TICK,   // 次tickで反映
    CONDITIONAL  // 条件成立時に反映
}
