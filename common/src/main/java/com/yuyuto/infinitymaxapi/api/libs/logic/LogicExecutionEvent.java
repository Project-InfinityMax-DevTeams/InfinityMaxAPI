package com.yuyuto.infinitymaxapi.api.libs.logic;

import com.yuyuto.infinitymaxapi.api.libs.behavior.BehaviorContext;
import com.yuyuto.infinitymaxapi.gamelibs.event.InfinityEvent;

/**
 * EventAPI(gamelibs/event) 経由で LogicID 実行を公開するイベント。
 */
public final class LogicExecutionEvent extends InfinityEvent {

    private final String logicId;
    private final BehaviorContext context;
    private final Object payload;

    /**
     * LogicID の実行イベントを初期化するコンストラクタ。
     *
     * @param logicId ロジックを一意に識別する ID
     * @param context 実行時の振る舞いコンテキスト（実行環境やログ情報を含む）
     * @param payload ロジックに渡す任意の追加データ（必要に応じて null を指定可能）
     */
    public LogicExecutionEvent(String logicId, BehaviorContext context, Object payload) {
        this.logicId = logicId;
        this.context = context;
        this.payload = payload;
    }

    /**
     * 実行対象のロジック識別子を取得する。
     *
     * @return 実行されるロジックの識別子文字列。
     */
    public String logicId() {
        return logicId;
    }

    /**
     * このイベントに関連付けられた実行コンテキスト（BehaviorContext）を取得する。
     *
     * @return イベントの実行コンテキストを表す {@link BehaviorContext}
     */
    public BehaviorContext context() {
        return context;
    }

    /**
     * 実行イベントに関連付けられたペイロードを返す。
     *
     * @return ペイロードオブジェクト。ペイロードが存在しない場合は`null`。
     */
    public Object payload() {
        return payload;
    }
}
