package com.yuyuto.infinitymaxapi.api.event;

import java.util.function.Consumer;

public final class EventListener<T extends ModEvent> {

    public final Consumer<T> consumer;
    public final EventPriority priority;
    public final boolean async;

    public EventListener(
            Consumer<T> consumer,
            EventPriority priority,
            boolean async
    ) {
        this.consumer = consumer;
        this.priority = priority;
        this.async = async;
    }
}