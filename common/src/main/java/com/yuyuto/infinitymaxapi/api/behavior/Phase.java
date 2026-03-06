package com.yuyuto.infinitymaxapi.api.behavior;

/**
 * 型安全の保証のためにここに対応型を列挙する。
 * いつ実行されるかというもの。意味は下を参照。
 */

public enum Phase {
    INIT,      // 初期化時
    TICK,      // 毎フレーム/毎tick
    INTERACT,  // ブロックやアイテムの操作時
    USE,       // アイテム使用時
    PRESS,     // キー入力
    RENDER,    // UI描画
    RECEIVE    // パケット受信
}
