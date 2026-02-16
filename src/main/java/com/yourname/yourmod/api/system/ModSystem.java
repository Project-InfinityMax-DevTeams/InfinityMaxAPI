package com.yourname.yourmod.api.system;

/**
 * DSLによって宣言されたシステムの実行ロジックを提供する共通インターフェース。
 */
public interface ModSystem {

    /**
     * 一意なシステムID。
     */
    String id();

    /**
     * システム起動時の初期化処理。
     */
    void initialize(SystemRuntime runtime);

    /**
     * シャットダウン時の後処理。
     */
    default void shutdown() {
    }
}
