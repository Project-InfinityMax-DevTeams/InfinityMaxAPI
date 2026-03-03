package com.yuyuto.infinitymaxapi.api.libs;

import com.yuyuto.infinitymaxapi.api.event.EventPriority;
import com.yuyuto.infinitymaxapi.api.event.ModEvent;
import com.yuyuto.infinitymaxapi.api.event.ModEventBus;
import com.yuyuto.infinitymaxapi.api.event.PlayerJoinEvent;
import com.yuyuto.infinitymaxapi.api.libs.behavior.BehaviorContext;
import com.yuyuto.infinitymaxapi.api.libs.logic.LogicExecutionEvent;
import com.yuyuto.infinitymaxapi.gamelibs.event.InfinityEventBus;

import java.util.function.Consumer;

/**
 * InfinityMaxAPI のイベント公開ヘルパ。
 *
 * <p>既存の ModEventBus 互換 API を維持しつつ、
 * LogicID の実行イベントを gamelibs/event (InfinityEventBus) へ通知する。
 * ロジック本体の保持・実行は行わない。</p>
 */
public final class Events {

    /**
 * ユーティリティ用のクラスでありインスタンス化を防ぐためのプライベートコンストラクタ。
 */
private Events() {}

    public static <T extends ModEvent> void on(Class<T> eventClass, Consumer<T> consumer) {
        ModEventBus.listen(eventClass, consumer);
    }

    public static <T extends ModEvent> void on(Class<T> eventClass, Consumer<T> consumer, EventPriority priority, boolean async) {
        ModEventBus.listen(eventClass, consumer, priority, async);
    }

    /**
     * プレイヤーがサーバーに参加したときに実行されるハンドラを登録する。
     *
     * @param consumer プレイヤー参加イベントを受け取り処理する `Consumer<PlayerJoinEvent>` ハンドラ
     */
    public static void playerJoin(Consumer<PlayerJoinEvent> consumer) {
        on(PlayerJoinEvent.class, consumer);
    }

    /**
         * LogicID の実行を Event API に通知する。
         *
         * <p>このメソッドは通知専用であり、実際のロジック実行は行わない。</p>
         *
         * @param logicId 実行された LogicID
         * @param context 実行時のコンテキスト（BehaviorContext）
         * @param payload イベントに含める任意のペイロードデータ（null 可）
         */
    public static void dispatchLogic(String logicId, BehaviorContext context, Object payload) {
        InfinityEventBus.post(new LogicExecutionEvent(logicId, context, payload));
    }
}
