package com.yuyuto.infinitymaxapi.api.libs.behavior;

/**
 * パケット用の振る舞い接続インターフェース。
 *
 * @param <T> パケットのペイロード型
 */
@FunctionalInterface
public interface PacketBehaviorConnector<T> {

    /**
     * パケット受信時に実行されるロジック。
     *
     * @param context DSL 宣言由来の実行コンテキスト
     * @param payload パケット内容
     */
    void execute(BehaviorContext context, T payload);
}
