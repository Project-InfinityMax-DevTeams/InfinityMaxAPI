package com.yuyuto.infinitymaxapi.api.logic;

import com.yuyuto.infinitymaxapi.api.libs.behavior.BehaviorContext;
import com.yuyuto.infinitymaxapi.gamelibs.event.InfinityEvent;
import java.util.Objects;

/**
 * EventAPI(gamelibs/event) 経由で LogicID 実行を公開するイベント。
 */
public final class LogicExecutionEvent extends InfinityEvent {

    private final String logicId;
    private final BehaviorContext context;
    private final Object payload;

    public LogicExecutionEvent(String logicId, BehaviorContext context, Object payload) {
        this.logicId = Objects.requireNonNull(logicId, "logicId");
        this.context = Objects.requireNonNull(context, "context");
        this.payload = payload;
    }

    public String logicId() {
        return logicId;
    }

    public BehaviorContext context() {
        return context;
    }

    public Object payload() {
        return payload;
    }
}
