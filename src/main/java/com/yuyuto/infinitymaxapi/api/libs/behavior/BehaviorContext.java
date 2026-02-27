package com.yuyuto.infinitymaxapi.api.libs.behavior;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 振る舞い実行時に Java ロジックへ渡されるコンテキスト。
 *
 * <p>このコンテキストは DSL 側で宣言された紐づけ情報を保持し、
 * 実行時にロジック側で参照するための共通情報を提供する。</p>
 */
public final class BehaviorContext {

    private final BehaviorBindingType type;
    private final String targetId;
    private final String resourceId;
    private final String phase;
    private final Map<String, Object> metadata;

    public BehaviorContext(
            BehaviorBindingType type,
            String targetId,
            String resourceId,
            String phase,
            Map<String, Object> metadata
    ) {
        this.type = Objects.requireNonNull(type, "type");
        this.targetId = Objects.requireNonNull(targetId, "targetId");
        this.resourceId = Objects.requireNonNull(resourceId, "resourceId");
        this.phase = Objects.requireNonNull(phase, "phase");
        this.metadata = Collections.unmodifiableMap(new HashMap<>(Objects.requireNonNull(metadata, "metadata")));
    }

    public BehaviorBindingType type() {
        return type;
    }

    public String targetId() {
        return targetId;
    }

    public String resourceId() {
        return resourceId;
    }

    public String phase() {
        return phase;
    }

    public Map<String, Object> metadata() {
        return metadata;
    }
}
