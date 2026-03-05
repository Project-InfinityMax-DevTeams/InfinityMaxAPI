package com.yuyuto.infinitymaxapi.api.libs.registry.settings;

/**
 * パケット登録用の最小静的設定。
 */
public final class PacketSettings {
    /** パケットの logical channel。 */
    public String channel = "main";

    /** チャネル互換性判定用。 */
    public String protocolVersion = "1";
}
