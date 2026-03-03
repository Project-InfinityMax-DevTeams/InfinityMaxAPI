package com.yuyuto.infinitymaxapi.api.libs.logic;

import com.yuyuto.infinitymaxapi.api.libs.Events;
import com.yuyuto.infinitymaxapi.api.libs.behavior.BehaviorConnector;
import com.yuyuto.infinitymaxapi.api.libs.behavior.BehaviorContext;
import com.yuyuto.infinitymaxapi.api.libs.behavior.PacketBehaviorConnector;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * MDK から渡された Java/Kotlin ロジックを LogicID 単位で一元管理するレジストリ。
 *
 * <p>DSL の外部構文は変更せず、内部的に LogicID を生成して保持するための層です。</p>
 */
public final class LogicRegistry {

    private static final Map<String, LogicExecutor> LOGICS = new ConcurrentHashMap<>();

    private LogicRegistry() {
    }

    /**
     * 通常の振る舞いロジックを LogicID で登録する。
     *
     * @param logicId 文字列ID。ここを変更するとロジックの識別子が変わる。
     * @param connector 実際に実行されるロジック。
     */
    public static void registerBehavior(String logicId, BehaviorConnector connector) {
        Objects.requireNonNull(logicId, "logicId");
        Objects.requireNonNull(connector, "connector");

        final LogicExecutor executor = (context, payload) -> connector.execute(context);
        LogicExecutor prev = LOGICS.putIfAbsent(logicId, executor);
        if (prev != null) {
            throw new IllegalStateException("LogicID already registered: " + logicId);
        }
    }

    /**
     * パケット振る舞いロジックを LogicID で登録する。
     *
     * @param logicId 文字列ID。ここを変更するとロジックの識別子が変わる。
     * @param connector パケット受信時に呼び出すロジック。
     * @param payloadType ペイロード型。
     * @param <T> パケット型。
     */

    public static <T> void registerPacket(String logicId, PacketBehaviorConnector<T> connector, Class<T> payloadType) {
        Objects.requireNonNull(logicId, "logicId");
        Objects.requireNonNull(connector, "connector");
        Objects.requireNonNull(payloadType, "payloadType");

        final LogicExecutor executor = (context, payload) -> {
            if (!payloadType.isInstance(payload)) {
                throw new IllegalArgumentException(
                        "Payload type mismatch for logicId. expected=" + payloadType.getName()
                                + ", actual=" + (payload == null ? "null" : payload.getClass().getName())
                );
            }
            connector.execute(context, payloadType.cast(payload));
        };

        LogicExecutor prev = LOGICS.putIfAbsent(logicId, executor);
        if (prev != null) {
            throw new IllegalStateException("LogicID already registered: " + logicId);
        }
    }

    /**
     * LogicID からロジックを解決して実行する。
     *
     * <p>実行責務はこのクラスに集約される。登録済みロジックを実行した後、
     * EventAPI へ通知する。</p>
     */
    public static void execute(String logicId, BehaviorContext context, Object payload) {
        Objects.requireNonNull(logicId, "logicId");
        Objects.requireNonNull(context, "context");

        LogicExecutor executor = LOGICS.get(logicId);
        if (executor == null) {
            throw new IllegalStateException("Unregistered logicId: " + logicId);
        }

        executor.execute(context,payload);

        Events.dispatchLogic(new LogicExecutionEvent(logicId, context, payload));
    }

    public static void execute(String logicId, BehaviorContext context) {  
        execute(logicId, context, null);
    }

    @FunctionalInterface
    public interface LogicExecutor {
        void execute(BehaviorContext context, Object payload);
    }
}
