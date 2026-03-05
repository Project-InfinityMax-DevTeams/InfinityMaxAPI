package com.yuyuto.infinitymaxapi.forgeimpl;

import com.yuyuto.infinitymaxapi.loader.LoaderExpectPlatform;

/** Forge 向けネットワークブリッジ。 */
public final class ForgeNetworkImpl implements LoaderExpectPlatform.Network {

    /**
     * Forge実装側のネットワークパケット登録エントリメソッド。
     *
     * <p>この実装は実際の登録処理を行わず、パケットの実登録は
     * {@link com.yuyuto.infinitymaxapi.forgeimpl.ForgeRegistriesImpl#commit()} に委譲される。</p>
     */
    @Override
    public void register() {
        // Packet の実登録は ForgeRegistriesImpl#commit() に集約する。
    }

    /**
     * クライアントからサーバーへパケットを送信するための呼び出しポイント（Forge 実装では無操作）。
     *
     * 実行時には何も送信しない（意図的に空実装）。
     *
     * @param packet 送信対象のパケットオブジェクト
     */
    @Override
    public <T> void sendToServer(T packet) {
    }

    @Override
    public <P, T> void sendToPlayer(P player, T packet) {
    }
}
