package com.yuyuto.infinitymaxapi.api.libs

import com.yuyuto.infinitymaxapi.api.libs.behavior.BehaviorBinding
import com.yuyuto.infinitymaxapi.api.libs.behavior.BehaviorBindingType
import com.yuyuto.infinitymaxapi.api.libs.behavior.BehaviorConnector
import com.yuyuto.infinitymaxapi.api.libs.behavior.BehaviorRegistry
import com.yuyuto.infinitymaxapi.api.libs.behavior.PacketBehaviorBinding
import com.yuyuto.infinitymaxapi.api.libs.behavior.PacketBehaviorConnector
import com.yuyuto.infinitymaxapi.api.libs.logic.LogicRegistry

/**
 * 振る舞い接続 DSL のエントリポイント。
 *
 * オブジェクト登録は既存 `registry {}` 側で行い、
 * この DSL は「ID + リソース + Java ロジック参照」を接続する責務のみを持つ。
 */
fun behavior(block: BehaviorScope.() -> Unit) {
    BehaviorScope().apply(block)
}

/**
 * Java 呼び出し向けブリッジ。
 */
object BehaviorApi {
    @JvmStatic
    fun behavior(block: BehaviorScope.() -> Unit) = com.yuyuto.infinitymaxapi.api.libs.behavior(block)

    @JvmStatic
    fun behaviorJava(block: java.util.function.Consumer<BehaviorScope>) {
        block.accept(BehaviorScope())
    }
}

/**
 * block/item/entity/keybind/ui/packet を統合した接続スコープ。
 */
@RegistryDsl
class BehaviorScope {

    // 検証用のプライベート関数を追加
    private fun requireTargetId(id: String) {
        require(id.isNotBlank()) { "target id must not be blank" }
    }

    /** ブロックIDとロジックを接続する。 */
    fun block(id: String, block: BehaviorBindingScope.() -> Unit) {
        register(BehaviorBindingType.BLOCK, id, block)
    }

    /** アイテムIDとロジックを接続する。 */
    fun item(id: String, block: BehaviorBindingScope.() -> Unit) {
        register(BehaviorBindingType.ITEM, id, block)
    }

    /** エンティティIDとロジックを接続する。 */
    fun entity(id: String, block: BehaviorBindingScope.() -> Unit) {
        register(BehaviorBindingType.ENTITY, id, block)
    }

    /** キーバインドIDとロジックを接続する。 */
    fun keybind(id: String, block: BehaviorBindingScope.() -> Unit) {
        register(BehaviorBindingType.KEYBIND, id, block)
    }

    /** UI IDとロジックを接続する。 */
    fun ui(id: String, block: BehaviorBindingScope.() -> Unit) {
        register(BehaviorBindingType.UI, id, block)
    }

    /**
     * パケット識別子と対応するロジックを登録する。
     *
     * 指定された `id` と `block` の内容から `PacketBehaviorBinding` を BehaviorRegistry に登録し、
     * 同じ論理識別子で LogicRegistry にパケットコネクタを登録する。
     * `block` 内で設定された `connector` は必須で、`logicId` が空の場合は `packet:<id>:<phase>` の形式で既定値が使用される。
     *
     * @param id 登録するパケットの識別子。
     * @param block PacketBehaviorBindingScope を構成するビルダーブロック。
     * @throws IllegalArgumentException `block` 内で `connector` が設定されていない場合。
     */
    inline fun <reified T : Any> packet(id: String, noinline block: PacketBehaviorBindingScope<T>.() -> Unit) {
        val definition = PacketBehaviorBindingScope<T>().apply(block)
        val connector = requireNotNull(definition.connector) { "packet connector is required" }

        BehaviorRegistry.registerPacket(
            PacketBehaviorBinding(
                id,
                definition.resourceId,
                definition.phase,
                definition.logicId.ifBlank { "packet:${id}:${definition.phase.name.lowercase()}" },
                definition.metadata,
                connector,
                T::class.java
            )
        )

        LogicRegistry.registerPacket(
            definition.logicId.ifBlank { "packet:${id}:${definition.phase.name.lowercase()}" },
            connector,
            T::class.java
        )
    }

    /**
     * 指定した種類とIDでBehaviorおよび対応するLogicを登録する。
     *
     * 与えられたブロックでBehaviorBindingScopeを構築し、connector と logicId を確定して
     * BehaviorRegistry に BehaviorBinding を登録し、同一の logicId を LogicRegistry に登録する。
     *
     * @param type 登録するバインディングの種類
     * @param id バインディングの識別子（空文字は許容されない）
     * @param block バインディング設定を行うDSLブロック
     * @throws IllegalArgumentException id が空文字の場合、またはブロック内で connector が未設定の場合
     */
    private fun register(type: BehaviorBindingType, id: String, block: BehaviorBindingScope.() -> Unit) {
        requireTargetId(id)  // ← ここで検証
        val definition = BehaviorBindingScope().apply(block)
        val connector = requireNotNull(definition.connector) { "$type connector is required" }
        val resolvedLogicId = definition.logicId.ifBlank { "${type.name.lowercase()}:${id}:${definition.phase.name.lowercase()}" }

        BehaviorRegistry.register(
            BehaviorBinding(
                type,
                id,
                definition.resourceId,
                definition.phase.name.lowercase(),
                resolvedLogicId,
                definition.metadata,
                connector
            )
        )

        LogicRegistry.registerBehavior(resolvedLogicId, connector)
    }
}

/**
 * block/item/entity/keybind/ui 共通の接続定義スコープ。
 */
@RegistryDsl
class BehaviorBindingScope {
    /** 外部リソースID。例: textures/gui/energy_meter */
    var resourceId: String = ""

    /** 任意の実行フェーズ。例: init / tick / interact */
    var phase: Phase = Phase.INIT

    /** Java ロジックへ渡す任意メタデータ。 */
    val metadata: MutableMap<String, Any> = linkedMapOf()

    /**
     * LogicID。
     * この文字列を変更すると EventAPI 側で公開されるロジック識別子が変わる。
     */
    var logicId: String = ""

    /** 実行ロジック（Java メソッド参照を想定）。 */
    var connector: BehaviorConnector? = null

    /** メタデータ追加ヘルパ。 */
    fun meta(key: String, value: Any) {
        metadata[key] = value
    }
}

/**
 * packet 専用の接続定義スコープ。
 */
@RegistryDsl
class PacketBehaviorBindingScope<T : Any> {
    /** 外部リソースID。 */
    var resourceId: String = ""

    /** 実行フェーズ。 */
    var phase: Phase = Phase.INIT

    /** Java ロジックへ渡す任意メタデータ。 */
    val metadata: MutableMap<String, Any> = linkedMapOf()

    /**
     * LogicID。
     * この文字列を変更すると EventAPI 側で公開されるロジック識別子が変わる。
     */
    var logicId: String = ""

    /** パケット用ロジック（Java メソッド参照を想定）。 */
    var connector: PacketBehaviorConnector<T>? = null

    /** メタデータ追加ヘルパ。 */
    fun meta(key: String, value: Any) {
        metadata[key] = value
    }
}
