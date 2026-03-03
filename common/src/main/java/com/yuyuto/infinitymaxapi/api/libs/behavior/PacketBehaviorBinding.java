package com.yuyuto.infinitymaxapi.api.libs.behavior;

import java.util.Objects;
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
    private final Map<String, Object> metadata;
    private final PacketBehaviorConnector<T> connector;
    private final Class<T> payloadType;
    private final BehaviorContext context;

    public PacketBehaviorBinding(
            String targetId,
            String resourceId,
            Phase phase,
            Map<String, Object> metadata,
            PacketBehaviorConnector<T> connector,
            Class<T> payloadType
    ) {
        this.targetId = targetId;
        this.resourceId = resourceId;
        this.phase = phase;
        this.metadata = Collections.unmodifiableMap(new HashMap<>(metadata));
        this.connector = connector;
        this.payloadType = payloadType;
        this.context = new BehaviorContext(
            BehaviorBindingType.PACKET, targetId, resourceId, phase, this.metadata
        );
    }

    public String targetId() {
        return targetId;
    }

    public String resourceId() {
        return resourceId;
    }

    public Phase phase() {
        return phase;
    }

    public Class<T> payloadType() {
        return payloadType;
    }

    public Map<String, Object> metadata() {
        return metadata;
    }

    public void execute(T payload) {
        connector.execute(context, payload);
    }
}
