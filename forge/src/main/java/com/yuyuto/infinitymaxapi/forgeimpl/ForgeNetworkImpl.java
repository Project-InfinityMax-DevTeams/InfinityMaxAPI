package com.yuyuto.infinitymaxapi.forgeimpl;

import com.yuyuto.infinitymaxapi.api.libs.packet.PacketRegistry;
import com.yuyuto.infinitymaxapi.api.libs.packet.SimplePacket;
import com.yuyuto.infinitymaxapi.api.libs.registry.settings.PacketSettings;
import com.yuyuto.infinitymaxapi.loader.LoaderExpectPlatform;
import com.yuyuto.infinitymaxapi.loader.Platform;

/** Forge 向けネットワークブリッジ。 */
public final class ForgeNetworkImpl implements LoaderExpectPlatform.Network {

    @Override
    public void register() {
        for (SimplePacket<?> packet : PacketRegistry.packets()) {
            PacketSettings settings = new PacketSettings();
            settings.direction = packet.direction;
            Platform.get().registries().registerPacket(packet.id, packet, settings);
        }
    }

    @Override
    public <T> void sendToServer(T packet) {
    }

    @Override
    public <P, T> void sendToPlayer(P player, T packet) {
    }
}
