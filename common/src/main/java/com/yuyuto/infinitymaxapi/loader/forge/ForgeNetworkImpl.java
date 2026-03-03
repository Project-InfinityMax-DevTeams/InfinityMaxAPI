package com.yuyuto.infinitymaxapi.loader.forge;

import com.yuyuto.infinitymaxapi.api.libs.packet.PacketRegistry;
import com.yuyuto.infinitymaxapi.api.libs.packet.SimplePacket;
import com.yuyuto.infinitymaxapi.loader.LoaderExpectPlatform;
import com.yuyuto.infinitymaxapi.loader.Platform;

/**
 * Forge 向けネットワークブリッジ。
 */
public final class ForgeNetworkImpl implements LoaderExpectPlatform.Network {

    @Override
    public void register() {
        for (SimplePacket<?> packet : PacketRegistry.packets()) {
            Platform.get().registries().packet(packet.id, packet);
        }
    }

    @Override
    public <T> void sendToServer(T packet) {
        throw new UnsupportedOperationException("Forge network sendToServer is not implemented yet.");
    }

    @Override
    public <P, T> void sendToPlayer(P player, T packet) {
        throw new UnsupportedOperationException("Forge network sendToPlayer is not implemented yet.");
    }
}
