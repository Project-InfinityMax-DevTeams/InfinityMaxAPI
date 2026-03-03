package com.yuyuto.infinitymaxapi.api.libs.registry.settings;

/** ワールド要素（ディメンション/バイオーム/構造物）登録用の静的設定。 */
public final class WorldSettings {
    /** ここを変更すると要素種別(例: dimension/biome/structure)が変わる。 */
    public String kind = "dimension";
    /** ここを変更すると生成優先度が変わる。 */
    public int order = 0;
}
