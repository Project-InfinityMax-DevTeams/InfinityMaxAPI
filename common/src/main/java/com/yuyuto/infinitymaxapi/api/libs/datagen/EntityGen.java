package com.yuyuto.infinitymaxapi.api.libs.datagen;

import com.yuyuto.infinitymaxapi.api.platform.PlatformDataGen;

/**
 * Legacy Java builder for entity-related DataGen definitions.
 */
public final class EntityGen extends BaseGen<EntityGen> {

    private Loot loot;
    private String lang;

    public EntityGen(String id) {
        super(id);
    }

    /** Assigns an entity loot table generator callback. */
    public EntityGen loot(Loot loot) {
        this.loot = loot;
        return this;
    }

    /** Assigns an entity display name for language generation. */
    public EntityGen lang(String name) {
        this.lang = name;
        return this;
    }

    @Override
    protected void submit() {
        PlatformDataGen.submitEntity(id, loot, lang);
    }
}
