package com.yuyuto.infinitymaxapi.api.libs

import com.yuyuto.infinitymaxapi.api.event.EventPriority
import com.yuyuto.infinitymaxapi.api.event.ModEvent
import com.yuyuto.infinitymaxapi.api.event.ModEventBus
import com.yuyuto.infinitymaxapi.api.libs.packet.PacketBuffer
import com.yuyuto.infinitymaxapi.api.libs.packet.PacketDirection
import com.yuyuto.infinitymaxapi.api.libs.packet.PacketHandler
import com.yuyuto.infinitymaxapi.api.libs.packet.PacketRegistry
import com.yuyuto.infinitymaxapi.api.libs.packet.SimplePacket
import kotlin.reflect.KClass

@DslMarker
annotation class RegistryDsl

fun registry(block: RegistryScope.() -> Unit) {
    RegistryScope().apply(block)
}

object RegistryApi {
    @JvmStatic
    fun registry(block: RegistryScope.() -> Unit) = com.yuyuto.infinitymaxapi.api.libs.registry(block)

    @JvmStatic
    fun registryJava(block: java.util.function.Consumer<RegistryScope>) {
        block.accept(RegistryScope())
    }
}

@RegistryDsl
class RegistryScope {
    fun <T : Any> item(id: String, template: T, block: ItemRegistration<T>.() -> Unit = {}): T {
        val reg = ItemRegistration(template).apply(block)  
        ModRegistries.registerItem(  
            id = id,  
            template = template,  
            stack = reg.stack,  
            durability = reg.durability,  
            tab = reg.tab  
        )
        return template
    }

    // Block の修正
    fun <T : Any> block(id: String, template: T, block: BlockRegistration<T>.() -> Unit = {}): T {
        val reg = BlockRegistration(template).apply(block)
        ModRegistries.registerBlock(id, template, reg.strength, reg.noOcclusion)
        return template
    }

    // Entity の修正
    fun <T : Any, C : Any> entity(id: String, template: T, block: EntityRegistration<C>.() -> Unit): T {
        val reg = EntityRegistration<C>().apply(block)
        val category = requireNotNull(reg.category) { "Entity category is required" }
        require(reg.width > 0f) { "Entity width must be > 0" }
        require(reg.height > 0f) { "Entity height must be > 0" }
        ModRegistries.registerEntity(id, template, category, reg.width, reg.height)
        return template
    }

    fun <T : ModEvent> event(type: KClass<T>, block: EventRegistration<T>.() -> Unit) {
        val definition = EventRegistration<T>().apply(block)
        val handler = requireNotNull(definition.handler) { "event handler is required" }
        ModEventBus.listen(type.java, handler, definition.priority, definition.async)
    }

    inline fun <reified T : ModEvent> event(noinline block: EventRegistration<T>.() -> Unit) {
        event(T::class, block)
    }

    fun <T : Any> packet(id: String, block: PacketRegistration<T>.() -> Unit) {
        val definition = PacketRegistration<T>().apply(block)
        val decoder = requireNotNull(definition.decoder) { "packet decoder is required" }
        val encoder = requireNotNull(definition.encoder) { "packet encoder is required" }
        val handler = requireNotNull(definition.handler) { "packet handler is required" }

        PacketRegistry.register(SimplePacket(id, definition.direction, decoder, encoder, handler))
    }
}

@RegistryDsl
class ItemRegistration<T : Any>(val template: T) {
    /** スタック上限。ここを変更するとアイテムの最大保持数が変わる。 */
    var stack: Int = 64
    /** 耐久値。ここを変更するとアイテムの耐久性が変わる。 */
    var durability: Int = 0
    var tab: Any? = null
}

@RegistryDsl
class BlockRegistration<T : Any>(val template: T) {
    /** 強度。ここを変更するとブロックの硬さが変わる。 */
    var strength: Float = 1.0f
    /** 遮蔽判定。true にすると透過/非遮蔽扱い。 */
    var noOcclusion: Boolean = false
}

@RegistryDsl
class EntityRegistration<C : Any> {
    /** カテゴリ。ここを変更するとエンティティの分類が変わる。 */
    var category: C? = null
    /** 幅。ここを変更すると当たり判定の横幅が変わる。 */
    var width: Float = 0.6f
    /** 高さ。ここを変更すると当たり判定の高さが変わる。 */
    var height: Float = 1.8f
}

@RegistryDsl
class EventRegistration<T : ModEvent> {
    var priority: EventPriority = EventPriority.NORMAL
    var async: Boolean = false
    var handler: java.util.function.Consumer<T>? = null
}

@RegistryDsl
class PacketRegistration<T : Any> {
    var direction: PacketDirection = PacketDirection.C2S
    var decoder: ((PacketBuffer) -> T)? = null
    var encoder: SimplePacket.PacketEncoder<T>? = null
    var handler: PacketHandler<T>? = null
}
