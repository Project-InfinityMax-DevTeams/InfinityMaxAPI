package com.yuyuto.infinitymaxapi.api.libs.datagen;

/**
 * Legacy Java-style DataGen entrypoint.
 *
 * <p>This API remains for backward compatibility. New integrations should prefer
 * the Kotlin DSL in {@code DataGenDsl.kt} and perform actual generation in the MDK side.
 */
public final class DataGen {

    private DataGen() {}

    /** Creates a block data definition builder. */
    public static BlockGen block(String id) {
        return new BlockGen(id);
    }

    /** Creates an item data definition builder. */
    public static ItemGen item(String id) {
        return new ItemGen(id);
    }

    /** Creates an entity data definition builder. */
    public static EntityGen entity(String id) {
        return new EntityGen(id);
    }
}
