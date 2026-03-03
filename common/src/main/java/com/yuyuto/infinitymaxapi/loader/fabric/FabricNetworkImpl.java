package com.yuyuto.infinitymaxapi.loader.fabric;

import com.yuyuto.infinitymaxapi.api.libs.packet.PacketRegistry;
import com.yuyuto.infinitymaxapi.api.libs.packet.SimplePacket;
import com.yuyuto.infinitymaxapi.loader.LoaderExpectPlatform;
import com.yuyuto.infinitymaxapi.loader.Platform;

/**
 * Fabric 向けネットワークブリッジ。
 */
public final class FabricNetworkImpl implements LoaderExpectPlatform.Network {

    /**
     * 登録済みの全ての SimplePacket をプラットフォームのパケットレジストリに登録する。
     *
     * PacketRegistry.packets() から取得した各 SimplePacket をその id で Platform のレジストリに登録します。
     */
    @Override
    public void register() {
        for (SimplePacket<?> packet : PacketRegistry.packets()) {
            Platform.get().registries().packet(packet.id, packet);
        }
    }

    /**
     * クライアントからサーバーへパケットを送信するためのエントリポイント。
     *
     * <p>現状は実装が未完で、呼び出しても何も行いません（Fabric のネットワーキング API 実装時に送信処理を追加します）。</p>
     *
     * @param packet 送信対象のパケットオブジェクト
     */
    @Override
    public <T> void sendToServer(T packet) {
        // 実送信は Fabric networking API 実装時にこの箇所へ追加する。
    }

    /**
     * 指定したプレイヤーにパケットを送信することを意図したメソッド。
     *
     * 現在は Fabric のネットワーキング実装が未適用のため実際の送信は行われない（プレースホルダ）。
     *
     * @param player 送信先のプレイヤーオブジェクト
     * @param packet 送信するパケットオブジェクト
     */
    @Override
    public <T> void sendToPlayer(T player, T packet) {
        // 実送信は Fabric networking API 実装時にこの箇所へ追加する。
    }
}
