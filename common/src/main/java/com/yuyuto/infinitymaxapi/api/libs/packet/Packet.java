package com.yuyuto.infinitymaxapi.api.libs.packet;

/**
 * common 側で完結する最小パケット契約。
 *
 * <p>Fabric / Forge の型はここで import しない。
 * 実バッファ型は各ローダー実装側で Object として渡す。</p>
 */
public interface Packet {

    enum Flow {
        C2S,
        S2C
    }

    Flow flow();

    /** 実送信用にパケット内容を書き込む。 */
    void encode(Object buffer);

    /** 受信したバッファから新しいパケット実体を復元する。 */
    Packet decode(Object buffer);

    /** サーバー受信時の処理。flow が C2S のパケットで利用する。 */
    default void handleC2S(Object server, Object player) {
    }

    /** クライアント受信時の処理。flow が S2C のパケットで利用する。 */
    default void handleS2C(Object client, Object player) {
    }
}
