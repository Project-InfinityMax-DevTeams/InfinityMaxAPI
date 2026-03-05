package com.yuyuto.infinitymaxapi.api.libs.datagen

/**
 * World-generator DataGen definition model.
 *
 * This file exists separately so worldgen definitions are explicit in the DataGen package
 * and easy to bind to loader-specific MDK generation flows.
 */
enum class WorldGeneratorCategory {
    CONFIGURED_FEATURE,
    PLACED_FEATURE,
    BIOME_MODIFIER,
    NOISE_SETTINGS,
    DENSITY_FUNCTION,
    STRUCTURE,
    STRUCTURE_SET
}

/** World generator definition. */
data class WorldGeneratorDefinition(
    val id: String,
    val category: WorldGeneratorCategory,
    val dependencies: List<String>,
    val custom: DataPayload
)

/** DSL builder for a world generator object definition. */
class WorldGeneratorDsl(private val id: String, private val category: WorldGeneratorCategory) {
    private val dependencies = mutableListOf<String>()
    private val custom = linkedMapOf<String, Any?>()

    /** Adds dependency id used by this worldgen object. */
    fun dependency(id: String) {
        dependencies += id
    }

    /** Adds custom JSON-like field. */
    fun custom(key: String, value: Any?) {
        custom[key] = value
    }

    internal fun build() = WorldGeneratorDefinition(id, category, dependencies.toList(), DataPayload(custom.toMap()))
}
