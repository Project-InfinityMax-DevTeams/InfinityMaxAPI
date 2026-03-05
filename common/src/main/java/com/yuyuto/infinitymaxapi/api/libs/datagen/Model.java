package com.yuyuto.infinitymaxapi.api.libs.datagen;

/** Callback used by legacy Java API to define model output. */
@FunctionalInterface
public interface Model {
    /**
     * Generates model data for the given id.
     *
     * @param id namespaced target id
     */
    void generate(String id);
}
