package com.yuyuto.infinitymaxapi.api.libs

import com.yuyuto.infinitymaxapi.api.behavior.BehaviorBindingType
import com.yuyuto.infinitymaxapi.api.behavior.Phase
import com.yuyuto.infinitymaxapi.api.registry.BehaviorDefinition
import com.yuyuto.infinitymaxapi.api.registry.BlockDefinition
import com.yuyuto.infinitymaxapi.api.registry.ItemDefinition
import com.yuyuto.infinitymaxapi.api.registry.RegistryDefinition
import com.yuyuto.infinitymaxapi.api.util.LogUtil.error

@DslMarker
annotation class RegistryDsl

fun  registry(block: RegistryScope.() ->  Unit): RegistryDefinition {
    val def = RegistryDefinition()
    RegistryScope(def).apply(block)
    return def
}

@RegistryDsl
class RegistryScope(private val def: RegistryDefinition) {
    fun <T:Any> item(
        id:String,
        template:T,
        block: ItemSettings.() -> Unit = {}
    ){

        val settings = ItemSettings().apply(block)

        val d = ItemDefinition(id, template)

        d.maxStack = settings.maxStack
        d.durability = settings.durability
        d.model = settings.model

        def.items[id] = d

        settings.behaviors.forEach{
            def.addBehavior(id, BehaviorBindingType.ITEM, it)
        }
    }

    fun <T:Any> block(
        id: String,
        template:T,
        block: BlockSettings.() -> Unit = {},
    ) {
        val settings = BlockSettings().apply(block)

        val d = BlockDefinition(id, template)

        d.hardness = settings.hardness
        d.resistance = settings.resistance
        d.model = settings.model
        d.loot = settings.loot

        def.addBlock(d)

        settings.behaviors.forEach{
            def.addBehavior(id, BehaviorBindingType.BLOCK, it)
        }
    }

    fun <T:Any> dataGen(
        id:String,
        template:T,
        block: DataGenSettings.() -> Unit = {}
    ) {
        def.dataGen[id] = DataGenDefinition(
            id,
            template,
            DataGenSettings().apply(block)
        )
    }

    fun <T:Any> network(
        id: String,
        template:T,
        block: NetworkSettings.() -> Unit = {}
    ) {
        def.networks[id] = NetworkDefinition(
            id,
            template,
            NetworkSettings().apply(block)
        )
    }
}

class BlockSettings{

    var hardness: Float = 1f
    var resistance: Float = 1f
    var model: String? = null
    var loot: String? = null

    internal var behaviors = mutableListOf<BehaviorDefinition>()

    fun on(phase: Phase, block: BehaviorScope.() -> Unit) {

        val scope = BehaviorScope(phase)
        scope.apply(block)

        behaviors += scope.build()
    }
}

class ItemSettings {

    var maxStack: Int = 64;
    var durability: Int = 0;

    var model: String? = null

    internal val behaviors = mutableMapOf<BehaviorDefinition>()

    fun on(phase: Phase, block: BehaviorScope.() -> Unit) {
        val scope = BehaviorScope(phase)
        scope.apply(block)

        behaviors += scope.build()
    }
}

class BehaviorScope(
    private val phase: Phase
){

    private var logicId: String? = null
    private val metadata = mutableMapOf<String, Any>()

    fun logic(id: String){
        logicId = id
    }

    fun meta(key: String, value: Any){
        metadata[key] = value
    }

    fun build(): BehaviorDefinition {

        return BehaviorDefinition(
            phase,
            logicId ?: error("logic not defined"),
            metadata
        )
    }
}

data class BehaviorDefinition(
    val phase: Phase,
    val logicId: String,
    val metadata: Map<String, Any>
)