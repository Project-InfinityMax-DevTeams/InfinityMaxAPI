package com.yuyuto.infinitymaxapi.gamelibs.event;

public abstract class CancelableEvent extends InfinityEvent {

    private boolean cancelled = false;

    public void cancel() {
        this.cancelled = true;
    }

    public boolean isCancelled() {
        return cancelled;
    }
}