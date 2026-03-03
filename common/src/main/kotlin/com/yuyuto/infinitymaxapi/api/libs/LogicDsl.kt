package com.yuyuto.infinitymaxapi.api.libs

import com.yuyuto.infinitymaxapi.api.event.EventPriority
import com.yuyuto.infinitymaxapi.api.event.ModEvent
import com.yuyuto.infinitymaxapi.api.event.ModEventBus
import com.yuyuto.infinitymaxapi.api.libs.behavior.BehaviorBindingType
import com.yuyuto.infinitymaxapi.api.libs.behavior.BehaviorContext
import com.yuyuto.infinitymaxapi.api.libs.logic.LogicRegistry
import kotlin.reflect.KClass

/**
 * Logic DSL のエントリポイント。
 *
 * Registry DSL から動的実行定義を分離し、Event -> LogicID のトリガ定義のみを担当する。
 */
fun logic(block: LogicScope.() -> Unit) {
    LogicScope().apply(block)
}

/** Java 呼び出し向けブリッジ。 */
object LogicApi {
    @JvmStatic
    fun logic(block: LogicScope.() -> Unit) = com.yuyuto.infinitymaxapi.api.libs.logic(block)

    @JvmStatic
    fun logicJava(block: java.util.function.Consumer<LogicScope>) {
        block.accept(LogicScope())
    }
}

@RegistryDsl
class LogicScope {

    fun <T : ModEvent> event(type: KClass<T>, block: EventLogicBindingScope<T>.() -> Unit) {
        val definition = EventLogicBindingScope<T>().apply(block)
        val resolvedLogicId = requireNotNull(definition.logicId) { "trigger(logicId) is required" }

        ModEventBus.listen(type.java, {
            val context = BehaviorContext(
                BehaviorBindingType.EVENT,
                type.simpleName ?: "event",
                "event/${type.simpleName ?: "event"}",
                definition.phase,
                definition.metadata
            )
            LogicRegistry.execute(resolvedLogicId, context, it)
        }, definition.priority, definition.async)
    }

    inline fun <reified T : ModEvent> event(noinline block: EventLogicBindingScope<T>.() -> Unit) {
        event(T::class, block)
    }
}

/**
 * Event から LogicID を起動する設定スコープ。
 */
@RegistryDsl
class EventLogicBindingScope<T : ModEvent> {
    /** LogicID。ここを変更すると実行されるロジックが変わる。 */
    internal var logicId: String? = null

    /** 優先度。ここを変更するとイベント処理順が変わる。 */
    var priority: EventPriority = EventPriority.NORMAL

    /** 非同期実行指定。 */
    var async: Boolean = false

    /** フェーズ。ここを変更するとコンテキスト上の実行段階識別が変わる。 */
    var phase: Phase = Phase.INTERACT

    /** 任意メタデータ。 */
    val metadata: MutableMap<String, Any> = linkedMapOf()

    /** Event -> LogicID 接続。 */
    fun trigger(logicId: String) {
        this.logicId = logicId
    }

    fun meta(key: String, value: Any) {
        metadata[key] = value
    }
}
