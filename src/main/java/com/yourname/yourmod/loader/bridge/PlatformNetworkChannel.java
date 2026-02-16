package com.yourname.yourmod.loader.bridge;

import com.yourname.yourmod.api.libs.packet.PacketRegistry;
import com.yourname.yourmod.api.system.network.MessageSpec;
import com.yourname.yourmod.api.system.network.NetworkChannel;
import com.yourname.yourmod.loader.Platform;

/**
 * 既存のloader network実装へ橋渡しするアダプタ。
 */
public final class PlatformNetworkChannel implements NetworkChannel {

    @Override
    public <T> void register(MessageSpec<T> spec) {
        // 現在のテンプレートではPacketRegistryが登録の集約点。
        // ここでは将来のDSL連携に向けてID重複確認のみ行う。
        PacketRegistry.packets().stream()
                .filter(packet -> packet.id.equals(spec.id()))
                .findAny()
                .ifPresent(packet -> {
                    throw new IllegalStateException("Duplicate packet id: " + packet.id);
                });
    }

    @Override
    public <T> void sendToServer(String messageId, T payload) {
        Platform.get().network().sendToServer(payload);
    }

    @Override
    public <T> void sendToPlayer(Object player, String messageId, T payload) {
        Platform.get().network().sendToPlayer(player, payload);
    }
}
