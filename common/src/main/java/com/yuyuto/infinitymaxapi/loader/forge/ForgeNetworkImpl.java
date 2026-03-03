package com.yuyuto.infinitymaxapi.loader.forge;

import com.yuyuto.infinitymaxapi.api.libs.packet.PacketRegistry;
import com.yuyuto.infinitymaxapi.api.libs.packet.SimplePacket;
import com.yuyuto.infinitymaxapi.loader.LoaderExpectPlatform;
import com.yuyuto.infinitymaxapi.loader.Platform;

/**
 * Forge 向けネットワークブリッジ。
 */
public final class ForgeNetworkImpl implements LoaderExpectPlatform.Network {

    /**
     * 登録済みのパケットをプラットフォーム側のパケットレジストリに登録する。
     *
     * PacketRegistry に含まれる各 SimplePacket をその ID とインスタンスで Platform のパケットレジストリに登録します。
     */
    @Override
    public void register() {
        for (SimplePacket<?> packet : PacketRegistry.packets()) {
            Platform.get().registries().packet(packet.id, packet);
        }
    }

    /**
     * クライアントからサーバへネットワークパケットを送信する。
     *
     * @param packet 送信するパケットオブジェクト。プラットフォーム対応のパケット実装を渡すこと。
     */
    @Override
    public <T> void sendToServer(T packet) {
        // 実送信は Forge networking API 実装時にこの箇所へ追加する。
    }

    /**
     * 指定したプレイヤーにパケットを送信することを目的としたメソッド。
     *
     * 現在は実装が未完了で実際の送信は行われない。Forge のネットワーキング API を統合した際に
     * 実送信ロジックを追加する。
     *
     * @param player 送信先のプレイヤーを表すオブジェクト（プラットフォーム固有のプレイヤー参照）
     * @param packet 送信するパケットオブジェクト（プラットフォーム固有のパケット形式）
     */
    @Override
    public <T> void sendToPlayer(T player, T packet) {
        // 実送信は Forge networking API 実装時にこの箇所へ追加する。
    }
}
