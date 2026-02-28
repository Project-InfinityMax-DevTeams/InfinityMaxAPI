package com.yuyuto.infinitymaxapi.api.libs;

import com.yuyuto.infinitymaxapi.api.event.EventPriority;
import com.yuyuto.infinitymaxapi.api.event.ModEvent;
import com.yuyuto.infinitymaxapi.api.event.ModEventBus;
import com.yuyuto.infinitymaxapi.api.event.PlayerJoinEvent;

import java.util.function.Consumer;

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
}
