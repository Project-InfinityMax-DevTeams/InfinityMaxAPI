package com.yourname.yourmod.api.event;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public final class ModEventBus {

    private static final Map<Class<?>, List<EventListener<?>>> LISTENERS = new HashMap<>();
    private static final ExecutorService ASYNC_EXECUTOR =
            Executors.newCachedThreadPool();

    private ModEventBus() {}

    public static <T extends ModEvent> void listen(
            Class<T> type,
            Consumer<T> consumer
    ) {
        listen(type, consumer, EventPriority.NORMAL, false);
    }

    public static <T extends ModEvent> void listen(
            Class<T> type,
            Consumer<T> consumer,
            EventPriority priority,
            boolean async
    ) {
        LISTENERS
                .computeIfAbsent(type, k -> new ArrayList<>())
                .add(new EventListener<>(consumer, priority, async));

        // priority順にソート
        LISTENERS.get(type).sort(
                Comparator.comparingInt(l -> -l.priority.level)
        );
    }

    @SuppressWarnings("unchecked")
    public static <T extends ModEvent> void post(T event) {
        List<EventListener<?>> listeners = LISTENERS.get(event.getClass());
        if (listeners == null) return;

        for (EventListener<?> listener : listeners) {
            EventListener<T> l = (EventListener<T>) listener;

            if (l.async) {
                ASYNC_EXECUTOR.submit(() -> l.consumer.accept(event));
            } else {
                l.consumer.accept(event);
            }
        }
    }
}