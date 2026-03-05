package com.yuyuto.infinitymaxapi.fabricimpl;

import com.yuyuto.infinitymaxapi.loader.LoaderExpectPlatform;

/** Fabric 向けネットワークブリッジ。 */
public final class FabricNetworkImpl implements LoaderExpectPlatform.Network {

    @Override
    public void register() {
        // Packet の実登録は FabricRegistriesImpl#commit() に集約する。
    }

    @Override
    public <T> void sendToServer(T packet) {
    }

    @Override
    public <P, T> void sendToPlayer(P player, T packet) {
    }
}
