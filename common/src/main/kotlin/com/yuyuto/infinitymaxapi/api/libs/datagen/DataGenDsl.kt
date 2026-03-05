package com.yuyuto.infinitymaxapi.api.libs.datagen

/**
 * Kotlin DSL entrypoint for creating DataGen definitions.
 *
 * This DSL is definition-only and does not execute generation.
 * Execution is delegated to MDK-side loader adapters.
 */
fun dataGen(block: DataGenDsl.() -> Unit): DataGenSpec = DataGenDsl().apply(block).build()

/** Immutable root object that stores all DataGen definitions produced by [dataGen]. */
data class DataGenSpec(
    val models: List<ModelDefinition>,
    val languages: List<LanguageDefinition>,
    val sounds: List<SoundDefinition>,
    val recipes: List<RecipeDefinition>,
    val lootTables: List<LootTableDefinition>,
    val tags: List<TagDefinition>,
    val advancements: List<AdvancementDefinition>,
    val globalLootModifiers: List<GlobalLootModifierDefinition>,
    val datapackRegistryObjects: List<DatapackRegistryObjectDefinition>,
    val worldGenerators: List<WorldGeneratorDefinition>
)

/** Builder for the root DataGen DSL. */
class DataGenDsl {
    private val models = mutableListOf<ModelDefinition>()
    private val languages = mutableListOf<LanguageDefinition>()
    private val sounds = mutableListOf<SoundDefinition>()
    private val recipes = mutableListOf<RecipeDefinition>()
    private val lootTables = mutableListOf<LootTableDefinition>()
    private val tags = mutableListOf<TagDefinition>()
    private val advancements = mutableListOf<AdvancementDefinition>()
    private val globalLootModifiers = mutableListOf<GlobalLootModifierDefinition>()
    private val datapackRegistryObjects = mutableListOf<DatapackRegistryObjectDefinition>()
    private val worldGenerators = mutableListOf<WorldGeneratorDefinition>()

    /** Registers a model JSON-like definition. */
    fun model(id: String, parent: String? = null, block: ModelDsl.() -> Unit = {}) {
        models += ModelDsl(id = id, parent = parent).apply(block).build()
    }

    /** Registers language entries for a locale. */
    fun language(locale: String, block: LanguageDsl.() -> Unit) {
        languages += LanguageDsl(locale).apply(block).build()
    }

    /** Registers a sound event definition. */
    fun sound(id: String, subtitle: String? = null, block: SoundDsl.() -> Unit = {}) {
        sounds += SoundDsl(id, subtitle).apply(block).build()
    }

    /** Registers a recipe definition. */
    fun recipe(id: String, type: String, block: RecipeDsl.() -> Unit = {}) {
        recipes += RecipeDsl(id, type).apply(block).build()
    }

    /** Registers a loot table definition. */
    fun lootTable(id: String, type: String, block: LootTableDsl.() -> Unit = {}) {
        lootTables += LootTableDsl(id, type).apply(block).build()
    }

    /** Registers a tag definition. */
    fun tag(id: String, registry: String, replace: Boolean = false, block: TagDsl.() -> Unit = {}) {
        tags += TagDsl(id, registry, replace).apply(block).build()
    }

    /** Registers an advancement definition. */
    fun advancement(id: String, parent: String? = null, block: AdvancementDsl.() -> Unit = {}) {
        advancements += AdvancementDsl(id, parent).apply(block).build()
    }

    /** Registers a global loot modifier definition. */
    fun globalLootModifier(id: String, type: String, block: GlobalLootModifierDsl.() -> Unit = {}) {
        globalLootModifiers += GlobalLootModifierDsl(id, type).apply(block).build()
    }

    /** Registers datapack registry object definition. */
    fun datapackRegistryObject(registry: String, key: String, block: DatapackRegistryObjectDsl.() -> Unit = {}) {
        datapackRegistryObjects += DatapackRegistryObjectDsl(registry, key).apply(block).build()
    }

    /** Registers world generator definition. */
    fun worldGenerator(id: String, category: WorldGeneratorCategory, block: WorldGeneratorDsl.() -> Unit = {}) {
        worldGenerators += WorldGeneratorDsl(id, category).apply(block).build()
    }

    internal fun build(): DataGenSpec = DataGenSpec(
        models = models.toList(),
        languages = languages.toList(),
        sounds = sounds.toList(),
        recipes = recipes.toList(),
        lootTables = lootTables.toList(),
        tags = tags.toList(),
        advancements = advancements.toList(),
        globalLootModifiers = globalLootModifiers.toList(),
        datapackRegistryObjects = datapackRegistryObjects.toList(),
        worldGenerators = worldGenerators.toList()
    )
}

/** Common key-value payload container used by many definitions. */
data class DataPayload(val values: Map<String, Any?>)

/** Model definition. */
data class ModelDefinition(
    val id: String,
    val parent: String?,
    val textures: Map<String, String>,
    val custom: DataPayload
)

/** Model section DSL. */
class ModelDsl(private val id: String, private val parent: String?) {
    private val textures = linkedMapOf<String, String>()
    private val custom = linkedMapOf<String, Any?>()

    /** Adds a model texture layer mapping. */
    fun texture(layer: String, value: String) {
        textures[layer] = value
    }

    /** Adds custom JSON-like field. */
    fun custom(key: String, value: Any?) {
        custom[key] = value
    }

    internal fun build() = ModelDefinition(id, parent, textures.toMap(), DataPayload(custom.toMap()))
}

/** Language definition. */
data class LanguageDefinition(val locale: String, val entries: Map<String, String>)

/** Language section DSL. */
class LanguageDsl(private val locale: String) {
    private val entries = linkedMapOf<String, String>()

    /** Adds a translation entry for this locale. */
    fun entry(key: String, value: String) {
        entries[key] = value
    }

    internal fun build() = LanguageDefinition(locale, entries.toMap())
}

/** Recipe definition. */
data class RecipeDefinition(
    val id: String,
    val type: String,
    val ingredients: List<RecipeIngredient>,
    val result: RecipeResult?,
    val custom: DataPayload
)

data class RecipeIngredient(val key: String?, val itemOrTag: String, val count: Int)
data class RecipeResult(val item: String, val count: Int)

/** Recipe section DSL. */
class RecipeDsl(private val id: String, private val type: String) {
    private val ingredients = mutableListOf<RecipeIngredient>()
    private var result: RecipeResult? = null
    private val custom = linkedMapOf<String, Any?>()

    /** Adds a recipe ingredient. */
    fun ingredient(itemOrTag: String, count: Int = 1, key: String? = null) {
        ingredients += RecipeIngredient(key, itemOrTag, count)
    }

    /** Sets recipe result item and amount. */
    fun result(item: String, count: Int = 1) {
        result = RecipeResult(item, count)
    }

    /** Adds custom JSON-like field. */
    fun custom(key: String, value: Any?) {
        custom[key] = value
    }

    internal fun build() = RecipeDefinition(id, type, ingredients.toList(), result, DataPayload(custom.toMap()))
}

/** Loot table definition. */
data class LootTableDefinition(
    val id: String,
    val type: String,
    val pools: List<LootPoolDefinition>,
    val custom: DataPayload
)

data class LootPoolDefinition(
    val rolls: Number,
    val entries: List<LootEntryDefinition>,
    val conditions: List<String>
)

data class LootEntryDefinition(
    val type: String,
    val name: String,
    val weight: Int?
)

/** Loot table section DSL. */
class LootTableDsl(private val id: String, private val type: String) {
    private val pools = mutableListOf<LootPoolDefinition>()
    private val custom = linkedMapOf<String, Any?>()

    /** Adds a loot pool. */
    fun pool(rolls: Number = 1, block: LootPoolDsl.() -> Unit) {
        pools += LootPoolDsl(rolls).apply(block).build()
    }

    /** Adds custom JSON-like field. */
    fun custom(key: String, value: Any?) {
        custom[key] = value
    }

    internal fun build() = LootTableDefinition(id, type, pools.toList(), DataPayload(custom.toMap()))
}

/** Loot pool section DSL. */
class LootPoolDsl(private val rolls: Number) {
    private val entries = mutableListOf<LootEntryDefinition>()
    private val conditions = mutableListOf<String>()

    /** Adds a loot entry. */
    fun entry(type: String, name: String, weight: Int? = null) {
        entries += LootEntryDefinition(type, name, weight)
    }

    /** Adds a condition identifier. */
    fun condition(id: String) {
        conditions += id
    }

    internal fun build() = LootPoolDefinition(rolls, entries.toList(), conditions.toList())
}

/** Tag definition. */
data class TagDefinition(
    val id: String,
    val registry: String,
    val replace: Boolean,
    val values: List<String>
)

/** Tag section DSL. */
class TagDsl(private val id: String, private val registry: String, private val replace: Boolean) {
    private val values = mutableListOf<String>()

    /** Adds a tag member value (item/block/entity/tag reference). */
    fun value(id: String) {
        values += id
    }

    internal fun build() = TagDefinition(id, registry, replace, values.toList())
}

/** Advancement definition. */
data class AdvancementDefinition(
    val id: String,
    val parent: String?,
    val criteria: Map<String, String>,
    val rewards: List<String>,
    val display: AdvancementDisplay?
)

data class AdvancementDisplay(
    val title: String,
    val description: String,
    val icon: String,
    val frame: String
)

/** Advancement section DSL. */
class AdvancementDsl(private val id: String, private val parent: String?) {
    private val criteria = linkedMapOf<String, String>()
    private val rewards = mutableListOf<String>()
    private var display: AdvancementDisplay? = null

    /** Adds an advancement criterion. */
    fun criterion(name: String, trigger: String) {
        criteria[name] = trigger
    }

    /** Adds a reward command/identifier. */
    fun reward(value: String) {
        rewards += value
    }

    /** Sets advancement display metadata. */
    fun display(title: String, description: String, icon: String, frame: String = "task") {
        display = AdvancementDisplay(title, description, icon, frame)
    }

    internal fun build() = AdvancementDefinition(id, parent, criteria.toMap(), rewards.toList(), display)
}

/** Global loot modifier definition. */
data class GlobalLootModifierDefinition(
    val id: String,
    val type: String,
    val conditions: List<String>,
    val custom: DataPayload
)

/** Global loot modifier section DSL. */
class GlobalLootModifierDsl(private val id: String, private val type: String) {
    private val conditions = mutableListOf<String>()
    private val custom = linkedMapOf<String, Any?>()

    /** Adds a global loot modifier condition id. */
    fun condition(id: String) {
        conditions += id
    }

    /** Adds custom JSON-like field. */
    fun custom(key: String, value: Any?) {
        custom[key] = value
    }

    internal fun build() = GlobalLootModifierDefinition(id, type, conditions.toList(), DataPayload(custom.toMap()))
}

/** Datapack registry object definition. */
data class DatapackRegistryObjectDefinition(
    val registry: String,
    val key: String,
    val custom: DataPayload
)

/** Datapack registry object section DSL. */
class DatapackRegistryObjectDsl(private val registry: String, private val key: String) {
    private val custom = linkedMapOf<String, Any?>()

    /** Adds custom JSON-like field. */
    fun custom(name: String, value: Any?) {
        custom[name] = value
    }

    internal fun build() = DatapackRegistryObjectDefinition(registry, key, DataPayload(custom.toMap()))
}
