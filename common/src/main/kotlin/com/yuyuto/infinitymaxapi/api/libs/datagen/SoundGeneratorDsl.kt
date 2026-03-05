package com.yuyuto.infinitymaxapi.api.libs.datagen

/**
 * Sound DataGen definition model.
 *
 * This file exists separately so sound generation responsibilities are discoverable
 * in the DataGen package and can be consumed by MDK adapters.
 */
data class SoundDefinition(
    val id: String,
    val subtitle: String?,
    val sounds: List<SoundClipDefinition>
)

/** One clip entry inside a sound event definition. */
data class SoundClipDefinition(
    val name: String,
    val stream: Boolean,
    val volume: Double?,
    val pitch: Double?,
    val weight: Int?
)

/** DSL builder for a sound event definition. */
class SoundDsl(private val id: String, private val subtitle: String?) {
    private val sounds = mutableListOf<SoundClipDefinition>()

    /** Adds a sound clip entry for this sound event. */
    fun clip(name: String, stream: Boolean = false, volume: Double? = null, pitch: Double? = null, weight: Int? = null) {
        sounds += SoundClipDefinition(name, stream, volume, pitch, weight)
    }

    internal fun build() = SoundDefinition(id, subtitle, sounds.toList())
}
