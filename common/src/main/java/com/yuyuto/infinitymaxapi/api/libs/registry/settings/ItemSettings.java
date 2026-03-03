package com.yuyuto.infinitymaxapi.api.libs.registry.settings;

/** アイテム登録用の静的設定。 */
public final class ItemSettings<T> {
    /** ここを変更すると最大スタック数が変わる。 */
    public int stack = 64;
    /** ここを変更すると耐久値が変わる。 */
    public int durability = 0;
    /** ここを変更するとクリエイティブタブ相当の分類が変わる。 */
    public T tab;
}
