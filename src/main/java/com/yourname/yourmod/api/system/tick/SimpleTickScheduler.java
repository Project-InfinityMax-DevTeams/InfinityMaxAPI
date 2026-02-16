package com.yourname.yourmod.api.system.tick;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * ローダーイベントからtick()を呼び出す前提の簡易スケジューラ。
 */
public final class SimpleTickScheduler implements TickScheduler {

    private final List<Entry> entries = new ArrayList<>();

    @Override
    public ScheduledTask schedule(long delayTicks, Runnable action) {
        Entry entry = new Entry(delayTicks, 0L, action);
        entries.add(entry);
        return entry;
    }

    @Override
    public ScheduledTask scheduleRepeating(long periodTicks, Runnable action) {
        Entry entry = new Entry(periodTicks, periodTicks, action);
        entries.add(entry);
        return entry;
    }

    @Override
    public void tick() {
        Iterator<Entry> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Entry entry = iterator.next();
            if (entry.cancelled) {
                iterator.remove();
                continue;
            }

            entry.remaining--;
            if (entry.remaining <= 0) {
                entry.action.run();
                if (entry.periodTicks > 0) {
                    entry.remaining = entry.periodTicks;
                } else {
                    iterator.remove();
                }
            }
        }
    }

    private static final class Entry implements ScheduledTask {

        private long remaining;
        private final long periodTicks;
        private final Runnable action;
        private boolean cancelled;

        private Entry(long remaining, long periodTicks, Runnable action) {
            this.remaining = Math.max(1, remaining);
            this.periodTicks = periodTicks;
            this.action = action;
        }

        @Override
        public void cancel() {
            cancelled = true;
        }

        @Override
        public boolean cancelled() {
            return cancelled;
        }
    }
}
