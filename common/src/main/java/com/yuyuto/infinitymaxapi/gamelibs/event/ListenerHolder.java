package com.yuyuto.infinitymaxapi.gamelibs.event;

public record ListenerHolder<T extends InfinityEvent>(EventPriority priority, InfinityEventListener<T> listener) implements Comparable<ListenerHolder<?>> {

    @Override
    public int compareTo(ListenerHolder<?> o) {
        return this.priority.ordinal() - o.priority.ordinal();
    }
}