package com.yuyuto.infinitymaxapi.api.behavior;

import java.util.Map;

public record Behavior(BehaviorBindingType type, String targetId, Phase phase, String logicId, Map<String, Object> metadata) {
    public Behavior(
            BehaviorBindingType type,
            String targetId,
            Phase phase,
            String logicId,
            Map<String, Object> metadata
    ){
        this.type = type;
        this.targetId = targetId;
        this.phase = phase;
        this.logicId = logicId;
        this.metadata = metadata == null ? Map.of() : Map.copyOf(metadata);
    }
}
