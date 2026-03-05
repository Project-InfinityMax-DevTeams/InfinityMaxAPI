package com.yuyuto.infinitymaxapi.api.libs.datagen;

/**
 * Lightweight tag key object used by legacy DataGen APIs.
 *
 * @param <T> logical tag target type
 */
public final class TagKey<T> {

    private final String id;

    public TagKey(String id) {
        this.id = id;
    }

    /** Returns the namespaced tag identifier. */
    public String id() {
        return id;
    }
}
