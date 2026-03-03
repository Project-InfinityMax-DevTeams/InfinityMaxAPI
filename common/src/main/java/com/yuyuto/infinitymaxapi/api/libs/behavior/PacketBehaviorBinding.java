package com.yuyuto.infinitymaxapi.api.libs.behavior;

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

    /**
     * パケット専用のビヘイビアバインディングを初期化するコンストラクタ。
     *
     * ビヘイビアの識別情報（ターゲットID・リソースID・フェーズ・ロジックID）、
     * メタデータ、コネクタ、及びペイロードの型情報を設定し、内部で不変のメタデータコピーと
     * PACKET 型の BehaviorContext（フェーズ名は小文字）を構築します。
     *
     * @param targetId    ビヘイビアの対象を識別する文字列
     * @param resourceId  リソースを識別する文字列
     * @param phase       実行フェーズを示す列挙値
     * @param logicId     実行するロジックを参照する識別子
     * @param metadata    バインディングに付随するメタデータ（コピーされ不変化される）
     * @param connector   デバッグ/参照用に保持するパケットコネクタ
     * @param payloadType ペイロードのクラス型情報
     */
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
            BehaviorBindingType.PACKET, targetId, resourceId, phase.name().toLowerCase(java.util.Locale.ROOT), this.metadata
        );
    }

    /**
 * バインディングが参照するターゲットの識別子を取得する。
 *
 * @return バインディングが参照するターゲットのID文字列。
 */
public String targetId() { return targetId; }
    /**
 * このバインディングに関連するリソースの識別子を返します。
 *
 * @return このバインディングのリソース識別子
 */
public String resourceId() { return resourceId; }
    /**
 * バインディングに関連付けられた実行フェーズを取得する。
 *
 * @return このバインディングの Phase 値。
 */
public Phase phase() { return phase; }
    /**
 * バインディングに関連付けられたロジックの識別子を返す。
 *
 * @return このバインディングで使用されるロジックの識別子文字列
 */
public String logicId() { return logicId; }
    /**
 * バインディングが扱うペイロードの型を取得する。
 *
 * @return このバインディングのペイロードのクラスオブジェクト
 */
public Class<T> payloadType() { return payloadType; }
    /**
 * バインディングに関連付けられたメタデータの不変ビューを返す。
 *
 * @return 作成時に設定されたキーが文字列、値がオブジェクトの変更不可マップ
 */
public Map<String, Object> metadata() { return metadata; }
    /**
 * DSLから渡された元の PacketBehaviorConnector を返す。
 *
 * @return バインディング作成時にキャプチャされた `PacketBehaviorConnector<T>`（デバッグや参照のために保持されたもの）
 */
public PacketBehaviorConnector<T> connector() { return connector; }

    /**
     * このバインディングに紐づくロジックを実行する。
     *
     * バインディングのコンテキストと与えられたペイロードを用いて、関連付けられたロジックを呼び出します。
     *
     * @param payload ロジックに渡すペイロード（型パラメータ `T`）
     */
    public void execute(T payload) {
        LogicRegistry.execute(logicId, context, payload);
    }
}
