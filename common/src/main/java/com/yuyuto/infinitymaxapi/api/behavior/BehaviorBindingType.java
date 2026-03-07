package com.yuyuto.infinitymaxapi.api.behavior;

/**
 * Behavior接続DSLで扱う接続対象の種類。
 *
 * <p>登録済みオブジェクトそのものではなく、
 * オブジェクトに紐づく「Behavior接続」の分類に利用する。</p>
 */
public enum BehaviorBindingType {
    BLOCK,
    ITEM,
    ENTITY,
    KEYBIND,
    UI,
    PACKET,
    EVENT
}
