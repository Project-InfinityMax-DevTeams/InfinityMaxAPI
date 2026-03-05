package com.yuyuto.infinitymaxapi.api.libs.datagen;

/** Callback used by legacy Java API to define loot table output. */
@FunctionalInterface
public interface Loot {
    /**
     * Generates loot data for the given id.
     *
     * @param id namespaced target id
     */
    void generate(String id);
}
