package com.yuyuto.infinitymaxapi.loader.fabric;

import com.yuyuto.infinitymaxapi.api.libs.packet.PacketRegistry;
import com.yuyuto.infinitymaxapi.api.libs.packet.SimplePacket;
import com.yuyuto.infinitymaxapi.loader.LoaderExpectPlatform;
import com.yuyuto.infinitymaxapi.loader.Platform;

/**
 * Fabric 向けネットワークブリッジ。
 */
public final class FabricNetworkImpl implements LoaderExpectPlatform.Network {

    @Override
    public void register() {
        for (SimplePacket<?> packet : PacketRegistry.packets()) {
            Platform.get().registries().packet(packet.id, packet);
        }
    }

    @Override
    public <T> void sendToServer(T packet) {
        // 実送信は Fabric networking API 実装時にこの箇所へ追加する。
    }

    @Override
    public <P, T> void sendToPlayer(P player, T packet) {
        // 実送信は Fabric networking API 実装時にこの箇所へ追加する。
    }
}
