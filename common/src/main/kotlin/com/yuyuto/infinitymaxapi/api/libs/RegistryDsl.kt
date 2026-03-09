package com.yuyuto.infinitymaxapi.api.libs

import com.yuyuto.infinitymaxapi.api.behavior.Phase
import com.yuyuto.infinitymaxapi.api.logic.Logic
import com.yuyuto.infinitymaxapi.api.registry.BehaviorDefinition
import com.yuyuto.infinitymaxapi.api.registry.BlockDefinition
import com.yuyuto.infinitymaxapi.api.registry.BlockTemplate
import com.yuyuto.infinitymaxapi.api.registry.EntityDefinition
import com.yuyuto.infinitymaxapi.api.registry.EntityTemplate
import com.yuyuto.infinitymaxapi.api.registry.ItemDefinition
import com.yuyuto.infinitymaxapi.api.registry.ItemTemplate
import com.yuyuto.infinitymaxapi.api.registry.LootDefinition
import com.yuyuto.infinitymaxapi.api.registry.ModelDefinition
import com.yuyuto.infinitymaxapi.api.registry.RegistryDefinition

@DslMarker
annotation class RegistryDsl

fun  registry(block: RegistryScope.() ->  Unit): RegistryDefinition {
    val def = RegistryDefinition()
    RegistryScope(def).apply(block)
    return def
}

@RegistryDsl
class RegistryScope(private val def: RegistryDefinition) {
    fun item(
        id:String,
        template: ItemTemplate,
        item: ItemSettings.() -> Unit = {}
    ){

        val settings = ItemSettings().apply(item)

        val d = ItemDefinition(id, template)

        d.maxStack = settings.maxStack
        d.durability = settings.durability
        d.model = settings.model

        def.addItem(d)

        settings.behaviors.forEach{
            d.addBehavior(it)
        }
    }

		fun block(
            id: String,
            template: BlockTemplate,
            block: BlockSettings.() -> Unit = {}
		) {
		    val settings = BlockSettings().apply(block)

		    val d = BlockDefinition(id, template)

		    d.hardness = settings.hardness
		    d.resistance = settings.resistance
		    d.model = settings.model
		    d.loot = settings.loot

		    def.addBlock(d)

		    settings.behaviors.forEach{
		        behavior -> d.addBehavior(behavior)
		    }

		    settings.renderer?.let {
		        d.addRenderer(it)
		    }
		}

    /**
    fun entity(
        id:String,
        template: EntityTemplate,
        entity: EntitySettings.() -> Unit = {}
    ){
        val settings = EntitySettings().apply(entity)

        val d = EntityDefinition(id, template)

        //value

        def.addEntity(d)

        settings.behaviors.forEach{
            def.addBehavior(id, BehaviorBindingType.ENTITY, it)
        }
    }
    */

}

class BlockSettings{

    var hardness: Float = 1f
    var resistance: Float = 1f
    var model: ModelDefinition? = null
    var loot: LootDefinition? = null

    var renderer: String? = null

    internal var behaviors = mutableListOf<BehaviorDefinition>()

    fun renderer(id: String){
        renderer = id
    }

    fun on(phase: Phase, block: BehaviorScope.() -> Unit) {

        val scope = BehaviorScope(phase)
        scope.apply(block)

        behaviors += scope.build()
    }
}

class ItemSettings {

    var maxStack: Int = 64
    var durability: Int = 0

    var model: ModelDefinition? = null

    internal val behaviors = mutableListOf<BehaviorDefinition>()

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
    private var logic: Logic? = null
    private val metadata = mutableMapOf<String, Any>()

    fun logic(l: Logic){
        logic = l
    }

    fun meta(key: String, value: Any){
        metadata[key] = value
    }

    fun build(): BehaviorDefinition {
        val l = logic ?: error("Behavior Logic not defined")
        return BehaviorDefinition(phase,l, metadata)
    }
}