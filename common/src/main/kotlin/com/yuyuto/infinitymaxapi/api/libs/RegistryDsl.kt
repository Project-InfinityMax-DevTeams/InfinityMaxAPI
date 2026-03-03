package com.yuyuto.infinitymaxapi.api.libs

import com.yuyuto.infinitymaxapi.api.libs.registry.ModRegistriesProvider
import com.yuyuto.infinitymaxapi.api.libs.registry.settings.*

/**
 * Registry DSL のマーカー。
 */
@DslMarker
annotation class RegistryDsl

/**
 * ゲーム要素の静的定義専用 DSL。
 *
 * - ID文字列を変更すると登録IDが変わる
 * - 数値設定を変更すると対応要素の特性が変わる
 */
fun registry(block: RegistryScope.() -> Unit) {
    RegistryScope().apply(block)
}

/** Java 呼び出し用ブリッジ。 */
object RegistryApi {
    @JvmStatic
    fun registry(block: RegistryScope.() -> Unit) = com.yuyuto.infinitymaxapi.api.libs.registry(block)

    @JvmStatic
    fun registryJava(block: java.util.function.Consumer<RegistryScope>) {
        block.accept(RegistryScope())
    }
}

/**
 * Registry の定義スコープ。
 *
 * <p>このスコープでは静的設定のみを扱い、event/logic の定義は行わない。</p>
 */
@RegistryDsl
class RegistryScope {

    fun <T : Any> item(id: String, template: T, block: ItemSettings<T>.() -> Unit = {}): T {
        val settings = ItemSettings<T>().apply(block)
        ModRegistriesProvider.get().registerItem(id, template, settings)
        return template
    }

    fun <T : Any> block(id: String, template: T, block: BlockSettings.() -> Unit = {}): T {
        val settings = BlockSettings().apply(block)
        ModRegistriesProvider.get().registerBlock(id, template, settings)
        return template
    }

    fun <T : Any, C : Any> entity(id: String, template: T, category: C, block: EntitySettings<C>.() -> Unit): T {
        val settings = EntitySettings<C>(category).apply(block)
        requireNotNull(settings.category) { "Entity category is required" }
        ModRegistriesProvider.get().registerEntity(id, template, settings)
        return template
    }

    fun <T : Any, B : Any> blockEntity(id: String, template: T, vararg blocks: B, block: BlockEntitySettings.() -> Unit = {}): T {
        val settings = BlockEntitySettings().apply(block)
        ModRegistriesProvider.get().registerBlockEntity(id, template, blocks, Array<B>, settings)
        return template
    }

    fun <T : Any> dataGen(id: String, template: T, block: DataGenSettings.() -> Unit = {}): T {
        val settings = DataGenSettings().apply(block)
        ModRegistriesProvider.get().registerDataGen(id, template, settings)
        return template
    }

    fun <T : Any> packet(id: String, template: T, block: PacketSettings.() -> Unit = {}): T {
        val settings = PacketSettings().apply(block)
        ModRegistriesProvider.get().registerPacket(id, template, settings)
        return template
    }

    fun <T : Any> network(id: String, template: T, block: NetworkSettings.() -> Unit = {}): T {
        val settings = NetworkSettings().apply(block)
        ModRegistriesProvider.get().registerNetwork(id, template, settings)
        return template
    }

    fun <T : Any> gui(id: String, template: T, block: GuiSettings.() -> Unit = {}): T {
        val settings = GuiSettings().apply(block)
        ModRegistriesProvider.get().registerGui(id, template, settings)
        return template
    }

    fun <T : Any> world(id: String, template: T, block: WorldSettings.() -> Unit = {}): T {
        val settings = WorldSettings().apply(block)
        ModRegistriesProvider.get().registerWorld(id, template, settings)
        return template
    }
}
