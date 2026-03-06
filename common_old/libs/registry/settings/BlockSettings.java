package com.yuyuto.infinitymaxapi.api.libs.registry.settings;

/** ブロック登録用の静的設定。 */
public final class BlockSettings {
    /** ここを変更するとブロックの硬さが変わる。 */
    public float strength = 1.0f;
    /** ここを true にすると非遮蔽設定になる。 */
    public boolean noOcclusion = false;
}
