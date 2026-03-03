package com.yuyuto.infinitymaxapi.api.libs.behavior;

import com.yuyuto.infinitymaxapi.api.libs.logic.LogicRegistry;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 振る舞い接続DSLの1エントリを保持する内部モデル。
 *
 * <p>登録オブジェクトの生成は行わず、ID・リソース・LogicID・実行ロジックの
 * 「接続定義」のみを保持する。</p>
 */
public final class BehaviorBinding {

    private final BehaviorBindingType type;
    private final String targetId;
    private final String resourceId;
    private final String phase;
    private final String logicId;
    private final Map<String, Object> metadata;
    private final BehaviorConnector connector;

    public BehaviorBinding(
            BehaviorBindingType type,
            String targetId,
            String resourceId,
            String phase,
            String logicId,
            Map<String, Object> metadata,
            BehaviorConnector connector
    ) {
        this.type = Objects.requireNonNull(type, "type");
        this.targetId = Objects.requireNonNull(targetId, "targetId");
        this.resourceId = Objects.requireNonNull(resourceId, "resourceId");
        this.phase = Objects.requireNonNull(phase, "phase");
        this.logicId = Objects.requireNonNull(logicId, "logicId");
        this.metadata = Collections.unmodifiableMap(new HashMap<>(Objects.requireNonNull(metadata, "metadata")));
        this.connector = Objects.requireNonNull(connector, "connector");
    }

    public BehaviorBindingType type() { return type; }
    public String targetId() { return targetId; }
    public String resourceId() { return resourceId; }
    public String phase() { return phase; }
    public String logicId() { return logicId; }
    public Map<String, Object> metadata() { return metadata; }
    public BehaviorConnector connector() { return connector; }

    /**
     * バインディングを LogicID 経由で実行する。
     */
    public void execute() {
        LogicRegistry.execute(logicId, new BehaviorContext(type, targetId, resourceId, phase, metadata), null);
    }
}
