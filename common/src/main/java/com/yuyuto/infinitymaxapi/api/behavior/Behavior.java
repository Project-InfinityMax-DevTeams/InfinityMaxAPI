package com.yuyuto.infinitymaxapi.api.behavior;

import com.yuyuto.infinitymaxapi.api.logic.Logic;

import java.util.Map;

public record Behavior(
        BehaviorBindingType type,
        String targetId,
        Phase phase,
        Logic logic,
        String logicId,
        Map<String, Object> metadata
) {

    public Behavior(
            BehaviorBindingType type,
            String targetId,
            Phase phase,
            Logic logic,
            String logicId,
            Map<String, Object> metadata
    ) {
        this.type = type;
        this.targetId = targetId;
        this.phase = phase;
        this.logic = logic;
        this.logicId = logicId;
        this.metadata = metadata == null ? Map.of() : Map.copyOf(metadata);
    }
}