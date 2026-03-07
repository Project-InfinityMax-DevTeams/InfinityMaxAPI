package com.yuyuto.infinitymaxapi.api.behavior;

import java.util.Objects;
import java.util.Map;

/**
 * 実行時に Java ロジックへ渡されるコンテキスト。
 *
 * <p>このコンテキストは DSL 側で宣言された紐づけ情報を保持し、
 * 実行時にロジック側で参照するための共通情報を提供する。</p>
 */
public record BehaviorContext(BehaviorBindingType type, String targetId, String resourceId, Phase phase,
                              Map<String, Object> metadata) {

    public BehaviorContext(
            BehaviorBindingType type,
            String targetId,
            String resourceId,
            Phase phase,
            Map<String, Object> metadata
    ) {
        this.type = Objects.requireNonNull(type, "type");
        this.targetId = Objects.requireNonNull(targetId, "targetId");
        this.resourceId = Objects.requireNonNull(resourceId, "resourceId");
        this.phase = Objects.requireNonNull(phase, "phase");
        this.metadata = Map.copyOf(Objects.requireNonNull(metadata, "metadata"));
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> type) {
        Object value = metadata.get(key);
        if (value == null) {
            return null;
        }
        if (!type.isInstance(value)) {
            throw new IllegalStateException("Metadata key '" + key + "' is not of type " + type.getName());
        }
        return (T) value;
    }
}
