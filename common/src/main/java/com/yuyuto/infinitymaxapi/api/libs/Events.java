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

    private Events() {}

    public static <T extends ModEvent> void on(Class<T> eventClass, Consumer<T> consumer) {
        ModEventBus.listen(eventClass, consumer);
    }

    public static <T extends ModEvent> void on(Class<T> eventClass, Consumer<T> consumer, EventPriority priority, boolean async) {
        ModEventBus.listen(eventClass, consumer, priority, async);
    }

    public static void playerJoin(Consumer<PlayerJoinEvent> consumer) {
        on(PlayerJoinEvent.class, consumer);
    }

    /**
     * LogicID 実行を EventAPI へ通知する。
     *
     * <p>このメソッドは通知専用であり、ロジック実行は行わない。</p>
     */
    public static void dispatchLogic(String logicId, BehaviorContext context, Object payload) {
        InfinityEventBus.post(new LogicExecutionEvent(logicId, context, payload));
    }
}
