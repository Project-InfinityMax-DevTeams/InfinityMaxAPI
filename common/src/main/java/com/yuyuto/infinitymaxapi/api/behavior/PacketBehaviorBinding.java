package com.yuyuto.infinitymaxapi.api.behavior;

import com.yuyuto.infinitymaxapi.api.libs.Phase;
import com.yuyuto.infinitymaxapi.api.libs.logic.LogicRegistry;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * パケット接続専用の内部定義。
 *
 * @param <T> ペイロード型
 */
public final class PacketBehaviorBinding<T> {

    private final String targetId;
    private final String resourceId;
    private final Phase phase;
    private final String logicId;
    private final Map<String, Object> metadata;
    /**
     * DSL で受け取った元コネクタ参照。
     *
     * <p>実行は LogicRegistry 経由で行うため、このフィールドは
     * 「登録情報の保持（デバッグ/参照用途）」として保持する。</p>
     */
    private final PacketBehaviorConnector<T> connector;
    private final Class<T> payloadType;
    private final BehaviorContext context;

    public PacketBehaviorBinding(
            String targetId,
            String resourceId,
            Phase phase,
            String logicId,
            Map<String, Object> metadata,
            PacketBehaviorConnector<T> connector,
            Class<T> payloadType
    ) {
        this.targetId = targetId;
        this.resourceId = resourceId;
        this.phase = phase;
        this.logicId = logicId;
        this.metadata = Collections.unmodifiableMap(new HashMap<>(metadata));
        this.connector = connector;
        this.payloadType = payloadType;
        this.context = new BehaviorContext(
            BehaviorBindingType.PACKET, targetId, resourceId, phase, this.metadata
        );
    }

    public String targetId() { return targetId; }
    public String resourceId() { return resourceId; }
    public Phase phase() { return phase; }
    public String logicId() { return logicId; }
    public Class<T> payloadType() { return payloadType; }
    public Map<String, Object> metadata() { return metadata; }
    public PacketBehaviorConnector<T> connector() { return connector; }

    public void execute(T payload) {
        LogicRegistry.execute(logicId, context, payload);
    }
}
