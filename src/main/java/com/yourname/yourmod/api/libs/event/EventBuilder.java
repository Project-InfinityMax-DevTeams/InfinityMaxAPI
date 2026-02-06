package com.yourname.yourmod.api.libs.event;

import com.yourname.yourmod.api.event.*;

import java.util.function.Consumer;

/**
 * fluent形式でイベント設定を行うDSL
 */
public final class EventBuilder<T extends ModEvent> {

    private final Class<T> eventClass;

    private EventPriority priority = EventPriority.NORMAL;
    private boolean async = false;

    EventBuilder(Class<T> eventClass) {
        this.eventClass = eventClass;
    }

    /**
     * 優先度設定
     */
    public EventBuilder<T> priority(EventPriority priority) {
        this.priority = priority;
        return this;
    }

    /**
     * 非同期実行
     */
    public EventBuilder<T> async() {
        this.async = true;
        return this;
    }

    /**
     * 同期実行（明示）
     */
    public EventBuilder<T> sync() {
        this.async = false;
        return this;
    }

    /**
     * イベント処理登録（最終確定）
     */
    public void handle(Consumer<T> consumer) {
        EventListener<T> listener =
                new EventListener<>(consumer, priority, async);

        ModEventBus.register(eventClass, listener);
    }
}