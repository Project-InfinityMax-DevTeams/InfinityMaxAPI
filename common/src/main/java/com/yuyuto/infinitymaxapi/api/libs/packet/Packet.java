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

    /**
 * パケットの通信方向を示す。
 *
 * @return パケットの通信方向（C2S または S2C）
 */
Flow flow();

    /**
 * パケットの内容を送信用バッファへ書き込む。
 *
 * @param buffer 実際のバッファ実装（ローダー実装が提供する）。パケットの送信データを書き込む対象。
 */
    void encode(Object buffer);

    /**
 * 受信バッファからパケットインスタンスを復元する。
 *
 * @param buffer 受信したシリアライズ済みデータを保持するバッファ。具象型は実行環境（ローダー）によって異なる。
 * @return 復元された Packet インスタンス
 */
    Packet decode(Object buffer);

    /**
     * サーバーがこのパケットを受信したときに実行される処理を定義する。
     *
     * デフォルト実装は何もしない。実装クラスは必要に応じてこのメソッドをオーバーライドして
     * サーバー側での処理（例：送信プレイヤーの状態更新やサーバーリソースの操作）を実装する。
     *
     * @param server 実行環境側のサーバーオブジェクト（実装依存の型）
     * @param player パケットを送信したプレイヤーを表すオブジェクト（実装依存の型）
     */
    default void handleC2S(Object server, Object player) {
    }

    /**
     * クライアントがサーバからのパケットを受信した際に実行される処理のフック。
     *
     * <p>このメソッドは、受信側がクライアントでかつパケットの流れが S2C の場合に呼び出されることを想定している。
     *
     * @param client 受信コンテキストまたはクライアント側の環境オブジェクト（実装依存の型）
     * @param player 受信したパケットに関連するクライアント側プレイヤーオブジェクト（実装依存の型）
     */
    default void handleS2C(Object client, Object player) {
    }
}
