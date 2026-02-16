package com.yourname.yourmod.api.system.tick;

public interface ScheduledTask {

    void cancel();

    boolean cancelled();
}
