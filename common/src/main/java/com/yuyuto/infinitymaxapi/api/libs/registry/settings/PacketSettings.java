package com.yuyuto.infinitymaxapi.api.libs.registry.settings;

import com.yuyuto.infinitymaxapi.api.libs.packet.PacketDirection;

/** パケット登録用の静的設定。 */
public final class PacketSettings {
    /** ここを変更するとパケット方向が変わる。 */
    public PacketDirection direction = PacketDirection.C2S;
    /** ここを変更すると互換性確認用のチャネル名が変わる。 */
    public String channel = "main";
}
