package com.yuyuto.infinitymaxapi.api.event;

public enum EventPriority {
    HIGH(100),
    NORMAL(0),
    LOW(-100);

    public final int level;

    EventPriority(int level) {
        this.level = level;
    }
}