package com.yuyuto.infinitymaxapi.api.libs.datagen;

import com.yuyuto.infinitymaxapi.api.platform.PlatformDataGen;
import java.util.ArrayList;
import java.util.List;

/**
 * Legacy Java builder for block-related DataGen definitions.
 */
public final class BlockGen extends BaseGen<BlockGen> {

    private Model model;
    private Loot loot;
    private final List<TagKey<?>> tags = new ArrayList<>();

    public BlockGen(String id) {
        super(id);
    }

    /** Assigns a block model generator callback. */
    public BlockGen model(Model model) {
        this.model = model;
        return this;
    }

    /** Assigns a block loot table generator callback. */
    public BlockGen loot(Loot loot) {
        this.loot = loot;
        return this;
    }

    /** Adds a block tag target. */
    public BlockGen tag(TagKey<?> tag) {
        tags.add(tag);
        return this;
    }

    @Override
    protected void submit() {
        PlatformDataGen.submitBlock(id, model, loot, tags);
    }
}
