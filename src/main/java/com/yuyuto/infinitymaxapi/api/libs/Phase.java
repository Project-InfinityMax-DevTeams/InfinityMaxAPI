package com.yuyuto.infinitymaxapi.api.libs;

public enum Phase {
    INIT,      // 初期化時
    TICK,      // 毎フレーム/毎tick
    INTERACT,  // ブロックやアイテムの操作時
    USE,       // アイテム使用時
    PRESS,     // キー入力
    RENDER,    // UI描画
    RECEIVE    // パケット受信
}