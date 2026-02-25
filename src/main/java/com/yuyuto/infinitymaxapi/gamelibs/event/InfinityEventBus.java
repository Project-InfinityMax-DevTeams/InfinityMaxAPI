package com.yuyuto.infinitymaxapi.gamelibs.event;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InfinityEventBus {

    private static final Map<Class<? extends InfinityEvent>,
            List<InfinityEventListener<?>>> listeners = new ConcurrentHashMap<>();

    /**
     * イベント登録
     */
    public static <T extends InfinityEvent> void register(
            Class<T> eventType,
            InfinityEventListener<T> listener
    ) {
        listeners
                .computeIfAbsent(eventType, k -> new ArrayList<>())
                .add(listener);
    }

    /**
     * イベント送信
     */
    @SuppressWarnings("unchecked")
    public static <T extends InfinityEvent> void post(T event) {

        List<InfinityEventListener<?>> eventListeners =
                listeners.get(event.getClass());

        if (eventListeners == null) {
            return; // 登録なし＝何もしない
        }

        for (InfinityEventListener<?> rawListener : eventListeners) {

            try {
                InfinityEventListener<T> listener =
                        (InfinityEventListener<T>) rawListener;

                listener.handle(event);

            } catch (Exception e) {

                // 🔥 絶対にクラッシュさせない
                System.err.println(
                        "[InfinityAPI] Event error in "
                                + event.getClass().getSimpleName()
                                + ": " + e.getMessage()
                );

                e.printStackTrace();
            }
        }
    }
}