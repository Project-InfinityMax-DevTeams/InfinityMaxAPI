package com.yuyuto.infinitymaxapi.api.libs

import com.yuyuto.infinitymaxapi.api.registry.BlockDefinition
import com.yuyuto.infinitymaxapi.api.registry.ItemDefinition

@DslMarker
annotation class RegistryDsl

fun  registry(block: RegistryScope.() ->  Unit): RegistryDefinition{
    val def = RegistryDefinition()
    RegistryScope(def).apply(block)
    return def
}

@RegistryDsl
class RegistryScope(
    privare val def: RegistryDefinition
) {
    fun <T:Any> item(
        id:String,
        template:T,
        block: ItemSettings.() -> Unit = {}
    ){
        def.items[id] = ItemDefinition(
            id,
            template,
            ItemSettings().apply(block)
        )
    }

    fun <T:Any> block(
        id: String,
        template:T,
        block: BlockSetting.() -> Unit = {}
    ) {
        def.blocks[id] = BlockDefinition(
            id,
            template,
            BlockSettings().apply(block)
        )
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