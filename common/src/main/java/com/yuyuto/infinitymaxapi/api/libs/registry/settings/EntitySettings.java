package com.yuyuto.infinitymaxapi.api.libs.registry.settings;

import java .util.Objects;

/** エンティティ登録用の静的設定。 */
public final class EntitySettings<C> {
    /** エンティティカテゴリ（必須）。 */
    public final C category;
    /** ここを変更すると当たり判定の横幅が変わる。 */
    public float width = 0.6f;
    /** ここを変更すると当たり判定の高さが変わる。 */
    public float height = 1.8f;

    public EntitySettings(C category) {
        this.category = requireNonNull(category, "category");
    }
}