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

    /**
     * インスタンス化を防ぐためのプライベートコンストラクタ。
     */
    private LogicRegistry() {
    }

    /**
     * 指定した LogicID に通常の振る舞い（ペイロードを扱わない）ロジックを登録する。
     *
     * @param logicId 登録するロジックの一意な識別子
     * @param connector 登録される振る舞いを実行するコールバック
     */
    public static void registerBehavior(String logicId, BehaviorConnector connector) {
        Objects.requireNonNull(logicId, "logicId");
        Objects.requireNonNull(connector, "connector");

        final LogicExecutor executor = (context, payload) -> connector.execute(context);
        LOGICS.put(logicId, executor);
    }

    /**
     * 指定した LogicID に対して、特定のペイロード型を受け取るパケット振る舞いを登録する。
     *
     * @param logicId ロジックを識別する文字列ID
     * @param connector 指定のペイロード型を受け取ったときに実行される処理
     * @param payloadType 受け付けるペイロードの型
     * @param <T> ペイロードの型パラメータ
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
     * 指定した LogicID に対応するロジックを解決して実行する。
     *
     * <p>実行前にイベントを通知し、その後に登録済みのロジックを呼び出す。登録されているロジックがない場合は何もしない。</p>
     *
     * @param logicId 実行対象の LogicID
     * @param context ロジック実行時のコンテキスト
     * @param payload ロジックに渡す任意のペイロード（任意の型を受け取る）
     */
    public static void execute(String logicId, BehaviorContext context, Object payload) {
        Events.dispatchLogic(logicId, context, payload);

        final LogicExecutor executor = LOGICS.get(logicId);
        if (executor != null) {
            executor.execute(context, payload);
        }
    }

    @FunctionalInterface
    public interface LogicExecutor {
        /**
 * 登録されたロジックを与えられた実行コンテキストとペイロードで起動する。
 *
 * 実装は渡された BehaviorContext を使って処理を行い、payload は任意のオブジェクトとして扱われる（必要に応じて実装側で型チェックやキャストを行う）。
 *
 * @param context 実行時に提供されるコンテキスト情報（環境・状態など）
 * @param payload ロジックに渡される追加データ。存在しない場合は null となり得る
 */
void execute(BehaviorContext context, Object payload);
    }
}
