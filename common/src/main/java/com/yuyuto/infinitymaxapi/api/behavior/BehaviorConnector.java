package com.yuyuto.infinitymaxapi.api.behavior;

/**
 * 振る舞い接続DSLで使う Java 側ロジック参照のための関数型インターフェース。
 *
 * <p>実装はラムダ/メソッド参照のどちらでも可能。
 * Kotlin DSL からは Java メソッド参照を受け取ることを想定している。</p>
 */
@FunctionalInterface
public interface BehaviorConnector {

    /**
     * 接続済み振る舞いを実行する。
     *
     * @param context DSL で宣言された接続情報を含む実行コンテキスト
     */
    void execute(BehaviorContext context);
}
