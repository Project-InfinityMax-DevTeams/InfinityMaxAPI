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
        ItemRegistration(template).apply(block)
        ModRegistries.registerItem(id, template)
        return template
    }

    fun <T : Any> block(id: String, template: T, block: BlockRegistration<T>.() -> Unit = {}): T {
        val definition = BlockRegistration(template).apply(block)
        ModRegistries.registerBlock(id, template, definition.strength, definition.noOcclusion)
        return template
    }

    fun <T : Any, C : Any> entity(id: String, template: T, block: EntityRegistration<C>.() -> Unit): T {
        val definition = EntityRegistration<C>().apply(block)
        val category = requireNotNull(definition.category) { "entity category is required" }
        ModRegistries.registerEntity(id, template, category, definition.width, definition.height)
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
    var stack: Int = 64
    var durability: Int = 0
    var tab: Any? = null
}

@RegistryDsl
class BlockRegistration<T : Any>(val template: T) {
    var strength: Float = 1.0f
    var noOcclusion: Boolean = false
}

@RegistryDsl
class EntityRegistration<C : Any> {
    var category: C? = null
    var width: Float = 0.6f
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
