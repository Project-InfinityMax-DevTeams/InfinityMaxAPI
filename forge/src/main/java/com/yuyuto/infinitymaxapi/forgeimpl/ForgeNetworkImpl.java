package com.yuyuto.infinitymaxapi.forgeimpl;

import com.yuyuto.infinitymaxapi.loader.LoaderExpectPlatform;

/** Forge 向けネットワークブリッジ。 */
public final class ForgeNetworkImpl implements LoaderExpectPlatform.Network {

    @Override
    public void register() {
        // Packet の実登録は ForgeRegistriesImpl#commit() に集約する。
    }

    @Override
    public <T> void sendToServer(T packet) {
    }

    @Override
    public <P, T> void sendToPlayer(P player, T packet) {
    }
}
