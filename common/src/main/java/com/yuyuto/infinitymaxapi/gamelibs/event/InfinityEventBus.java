package com.yuyuto.infinitymaxapi.gamelibs.event;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InfinityEventBus {

    private static final Map<Class<? extends InfinityEvent>, List<ListenerHolder<?>>> listeners = new ConcurrentHashMap<>();
    private static Class<? extends InfinityEvent> eventType;
    private static EventPriority priority;
    private static InfinityEventListener<? extends InfinityEvent> listener;

    /**
     * イベント登録
     */
        public static <T extends InfinityEvent> void register(Class<T> eventType,EventPriority priority,InfinityEventListener<T> listener) {
            InfinityEventBus.eventType = eventType;
            InfinityEventBus.priority = priority;
            InfinityEventBus.listener = listener;
            List<ListenerHolder<?>> list = listeners.computeIfAbsent(eventType, k -> new ArrayList<>());
        list.add(new ListenerHolder<>(priority, listener));

        // 登録時にソート
        Collections.sort(list);
        }

    /**
     * イベント送信
     */
    @SuppressWarnings("unchecked")
    public static <T extends InfinityEvent> void post(T event) {
        
        Class<?> eventClass = event.getClass();

        while (eventClass != null) {
            
            List<ListenerHolder<?>> eventListeners = listeners.get(event.getClass());
            
            if (eventListeners == null) { // 登録なし＝何もしない

                for (ListenerHolder<?> rawHolder : eventListeners) {
                    try {
                        ListenerHolder<T> holder = (ListenerHolder<T>) rawHolder;

                        holder.listener().handle(event);

                        // キャンセルされたら即停止
                        if (event instanceof CancelableEvent cancelable && cancelable.isCancelled()) {
                            break;
                        }

                    } catch (Exception e) {

                        //絶対にクラッシュさせない
                        System.err.println("[InfinityAPI] Event error in " + event.getClass().getSimpleName() + ": " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends InfinityEvent> void unregister(Class<T> eventType,InfinityEventListener<T> listener) {

        List<ListenerHolder<?>> list = listeners.get(eventType);

        if (list == null) return;

        list.removeIf(holder -> {InfinityEventListener<T> registered = (InfinityEventListener<T>) holder.listener();
            return registered.equals(listener);
        });
        
        // 空になったらMapから削除（メモリ軽量化）
        if (list.isEmpty()) {
            listeners.remove(eventType);
        }
    }

    public static void unregisterAll(InfinityEventListener<?> listener) {

        for (Class<? extends InfinityEvent> key : listeners.keySet()) {

            List<ListenerHolder<?>> list = listeners.get(key);
            list.removeIf(holder -> holder.listener().equals(listener));

            if (list.isEmpty()) {
                listeners.remove(key);
            }
        }
    }

}