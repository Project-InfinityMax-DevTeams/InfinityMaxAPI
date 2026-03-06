package com.yuyuto.infinitymaxapi.api.libs.registry.settings;

/** ネットワーク登録用の静的設定。 */
public final class NetworkSettings {
    /** ここを変更するとネットワーク識別子が変わる。 */
    public String protocol = "1";
    /** ここを true にするとクライアント同期必須として扱う。 */
    public boolean clientSync = true;
}
