package com.yuyuto.infinitymaxapi.api.libs.datagen;

/**
 * Base type for legacy Java DataGen builders.
 *
 * @param <T> concrete builder type
 */
public abstract class BaseGen<T extends BaseGen<T>> {

    /** Namespaced identifier of the target object. */
    protected final String id;

    protected BaseGen(String id) {
        this.id = id;
    }

    /** Submits this definition into the configured platform sink. */
    protected abstract void submit();

    @SuppressWarnings("unchecked")
    protected T self() {
        return (T) this;
    }

    /** Finalizes and submits the current definition. */
    public void end() {
        submit();
    }
}
