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

    /**
     * 指定したプロパティで BehaviorBinding インスタンスを初期化する。
     *
     * @param type バインディングの種類
     * @param targetId バインディングの対象を識別するID
     * @param resourceId 関連リソースのID
     * @param phase 実行フェーズを表す文字列
     * @param logicId 実行するロジックを識別するID
     * @param metadata バインディングに付与するメタデータ（不変マップとして保持される）
     * @param connector バインディングに関連付けられたコネクタ
     * @throws NullPointerException 引数がnullの場合（type、targetId、resourceId、phase、logicId、metadata、connectorのいずれか）
     */
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

    /**
 * バインディングの種類を取得する。
 *
 * @return このバインディングの種類を示す {@link BehaviorBindingType}
 */
public BehaviorBindingType type() { return type; }
    /**
 * バインディングが適用される対象の識別子を返す。
 *
 * @return 対象の識別子
 */
public String targetId() { return targetId; }
    /**
 * バインディングが参照するリソースの識別子を返す。
 *
 * @return バインディングのリソース識別子
 */
public String resourceId() { return resourceId; }
    /**
 * このバインディングの実行フェーズを返す。
 *
 * @return バインディングが適用されるフェーズ名
 */
public String phase() { return phase; }
    /**
 * バインディングが参照するロジックの識別子を取得する。
 *
 * @return バインディング実行時に参照されるロジックを一意に識別する文字列（logicId）。
 */
public String logicId() { return logicId; }
    /**
 * バインディングに関連付けられたメタデータの変更不可マップを取得する。
 *
 * @return バインディングに関連するキー/値ペアを保持する `Map<String, Object>`（変更不可）
 */
public Map<String, Object> metadata() { return metadata; }
    /**
 * バインディングに関連付けられた BehaviorConnector を取得する。
 *
 * @return このバインディングに紐づく {@link BehaviorConnector}
 */
public BehaviorConnector connector() { return connector; }

    /**
     * バインディングが参照する Logic ID に対応するロジックを、このバインディングの属性を反映した BehaviorContext で実行する。
     *
     * <p>BehaviorContext はこのバインディングの type、targetId、resourceId、phase、metadata を用いて構築される。</p>
     */
    public void execute() {
        LogicRegistry.execute(logicId, new BehaviorContext(type, targetId, resourceId, phase, metadata), null);
    }
}
