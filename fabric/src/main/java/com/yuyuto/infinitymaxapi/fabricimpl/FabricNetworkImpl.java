package com.yuyuto.infinitymaxapi.fabricimpl;

import com.yuyuto.infinitymaxapi.loader.LoaderExpectPlatform;

/** Fabric 向けネットワークブリッジ。 */
public final class FabricNetworkImpl implements LoaderExpectPlatform.Network {

    /**
     * ネットワークパケットの登録を担うエントリポイント。ただし Fabric 実装では登録処理をここで行わず、
     * 別箇所に集約されているためこの実装は空となる。
     */
    @Override
    public void register() {
        // Packet の実登録は FabricRegistriesImpl#commit() に集約する。
    }

    /**
     * 指定したパケットをサーバーへ送信する。
     *
     * @param packet 送信するパケットオブジェクト
     */
    @Override
    public <T> void sendToServer(T packet) {
    }

    @Override
    public <P, T> void sendToPlayer(P player, T packet) {
    }
}
