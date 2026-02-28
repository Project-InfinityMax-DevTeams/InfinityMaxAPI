package com.yuyuto.infinitymaxapi.gamelibs.event;

public class ListenerHolder<T extends InfinityEvent>implements Comparable<ListenerHolder<?>> {

    private final EventPriority priority;
    private final InfinityEventListener<T> listener;

    public ListenerHolder(EventPriority priority,InfinityEventListener<T> listener) {
        this.priority = priority;
        this.listener = listener;
    }

    public EventPriority getPriority() {
        return priority;
    }

    public InfinityEventListener<T> getListener() {
        return listener;
    }

    @Override
    public int compareTo(ListenerHolder<?> o) {
        return this.priority.ordinal() - o.priority.ordinal();
    }
}