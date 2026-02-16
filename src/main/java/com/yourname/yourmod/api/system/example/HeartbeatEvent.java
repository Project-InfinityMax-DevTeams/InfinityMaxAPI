package com.yourname.yourmod.api.system.example;

import com.yourname.yourmod.api.event.ModEvent;

public final class HeartbeatEvent implements ModEvent {

    public final String systemId;
    public final int ticks;

    public HeartbeatEvent(String systemId, int ticks) {
        this.systemId = systemId;
        this.ticks = ticks;
    }
}
