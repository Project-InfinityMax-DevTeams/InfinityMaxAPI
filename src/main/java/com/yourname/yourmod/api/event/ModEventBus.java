package com.yourname.yourmod.api.event;

import java.util.*;
import java.util.function.Consumer;

public final class ModEventBus {

    private static final Map<Class<?>, List<Consumer<?>>> LISTENERS = new HashMap<>();

    public static <T extends ModEvent> void listen(
            Class<T> type, Consumer<T> listener
    ) {
        LISTENERS
                .computeIfAbsent(type, k -> new ArrayList<>())
                .add(listener);
    }

    @SuppressWarnings("unchecked")
    public static <T extends ModEvent> void post(T event) {
        List<Consumer<?>> consumers = LISTENERS.get(event.getClass());
        if (consumers == null) return;

        for (Consumer<?> consumer : consumers) {
            ((Consumer<T>) consumer).accept(event);
        }
    }
}