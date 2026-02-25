package com.yuyuto.infinitymaxapi.gamelibs.event;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InfinityEventBus {

    private static final Map<Class<? extends InfinityEvent>, List<ListenerHolder<?>>> listeners = new ConcurrentHashMap<>();
    /**
     * イベント登録
     */
        public static <T extends InfinityEvent> void register(Class<T> eventType,EventPriority priority,InfinityEventListener<T> listener) {
            listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(new ListenerHolder<>(priority, listener));
        }

    /**
     * イベント送信
     */
    @SuppressWarnings("unchecked")
    public static <T extends InfinityEvent> void post(T event) {

        List<InfinityEventListener<?>> eventListeners = listeners.get(event.getClass());

        if (eventListeners == null) return; // 登録なし＝何もしない

            // 優先度順にソート（HIGH → LOW）
        eventListeners.sort(Comparator.comparing(holder -> ((ListenerHolder<?>) holder).getPriority()));

        for (ListenerHolder<?> rawHolder : eventListeners) {

            try {
                ListenerHolder<T> holder = (ListenerHolder<T>) rawHolder;

                holder.getListener().handle(event);

                // キャンセルされたら即停止
                if (event instanceof CancelableEvent cancelable
                        && cancelable.isCancelled()) {
                    break;
                }

            } catch (Exception e) {

                //  絶対にクラッシュさせない
                System.err.println("[InfinityAPI] Event error in " + event.getClass().getSimpleName() + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}