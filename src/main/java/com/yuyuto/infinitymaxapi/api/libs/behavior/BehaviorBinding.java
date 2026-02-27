package com.yuyuto.infinitymaxapi.api.libs.behavior;

import java.util.Objects;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 振る舞い接続DSLの1エントリを保持する内部モデル。
 *
 * <p>登録オブジェクトの生成は行わず、ID・リソース・実行ロジックの
 * 「接続定義」のみを保持する。</p>
 */
public final class BehaviorBinding {

    private final BehaviorBindingType type;
    private final String targetId;
    private final String resourceId;
    private final String phase;
    private final Map<String, Object> metadata;
    private final BehaviorConnector connector;

    public BehaviorBinding(
            BehaviorBindingType type,
            String targetId,
            String resourceId,
            String phase,
            Map<String, Object> metadata,
            BehaviorConnector connector
    ) {
        this.type = Objects.requireNonNull(type, "type");
        this.targetId = Objects.requireNonNull(targetId, "targetId");
        this.resourceId = Objects.requireNonNull(resourceId, "resourceId");
        this.phase = Objects.requireNonNull(phase, "phase");
        this.metadata = Collections.unmodifiableMap(new HashMap<>(Objects.requireNonNull(metadata, "metadata")));
        this.connector = Objects.requireNonNull(connector, "connector");
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

    public BehaviorConnector connector() {
        return connector;
    }

    /**
     * バインディングを実行する。
     */
    public void execute() {
        connector.execute(new BehaviorContext(type, targetId, resourceId, phase, metadata));
    }
}
