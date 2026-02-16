package com.yourname.yourmod.api.system.tick;

public interface TickScheduler {

    ScheduledTask schedule(long delayTicks, Runnable action);

    ScheduledTask scheduleRepeating(long periodTicks, Runnable action);

    void tick();
}
