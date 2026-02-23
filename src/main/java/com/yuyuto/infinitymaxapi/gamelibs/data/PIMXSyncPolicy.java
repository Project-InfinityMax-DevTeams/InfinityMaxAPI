package com.yuyuto.infinitymaxapi.gamelibs.data;

/**
 * データ同期ルールを定義
 */
public enum PIMXSyncPolicy {
    IMMEDIATE,  // 即時反映
    WAIT,       // 条件が揃うまで待機
    REPLACE,    // 上書き許可
    ERROR       // 不整合なら例外
}
