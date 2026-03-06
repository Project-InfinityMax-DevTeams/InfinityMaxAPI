package com.yuyuto.infinitymaxapi.api.logic;

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
        LOGICS.put(logicId, executor);
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
            if (payloadType.isInstance(payload)) {
                connector.execute(context, payloadType.cast(payload));
            }
        };

        LOGICS.put(logicId, executor);
    }

    /**
     * LogicID からロジックを解決して実行する。
     *
     * <p>実行責務はこのクラスに集約される。実行時には先に EventAPI へ通知し、
     * その後に登録済みロジックを実行する。</p>
     */
    public static void execute(String logicId, BehaviorContext context, Object payload) {
        Events.dispatchLogic(logicId, context, payload);
        Objects.requireNonNull(logicId, "logicId");
        Objects.requireNonNull(context, "context");

        final LogicExecutor executor = LOGICS.get(logicId);
        if (executor != null) {
            executor.execute(context, payload);
        }
    }

    @FunctionalInterface
    public interface LogicExecutor {
        void execute(BehaviorContext context, Object payload);
    }
}
