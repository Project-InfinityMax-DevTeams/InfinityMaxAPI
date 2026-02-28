package com.yuyuto.infinitymaxapi.api.libs.behavior;

/**
 * 振る舞い接続DSLで扱う接続対象の種類。
 *
 * <p>登録済みオブジェクトそのものではなく、
 * オブジェクトに紐づく「振る舞い接続」の分類に利用する。</p>
 */
public enum BehaviorBindingType {
    BLOCK,
    ITEM,
    ENTITY,
    KEYBIND,
    UI,
    PACKET
}
