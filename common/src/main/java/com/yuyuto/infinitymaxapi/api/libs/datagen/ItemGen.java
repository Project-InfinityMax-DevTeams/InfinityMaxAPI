package com.yuyuto.infinitymaxapi.api.libs.datagen;

import com.yuyuto.infinitymaxapi.api.platform.PlatformDataGen;
import java.util.ArrayList;
import java.util.List;

/**
 * Legacy Java builder for item-related DataGen definitions.
 */
public final class ItemGen extends BaseGen<ItemGen> {

    private Model model;
    private String lang;
    private final List<TagKey<?>> tags = new ArrayList<>();

    public ItemGen(String id) {
        super(id);
    }

    /** Assigns an item model generator callback. */
    public ItemGen model(Model model) {
        this.model = model;
        return this;
    }

    /** Adds an item tag target. */
    public ItemGen tag(TagKey<?> tag) {
        tags.add(tag);
        return this;
    }

    /** Assigns an item display name for language generation. */
    public ItemGen lang(String name) {
        this.lang = name;
        return this;
    }

    @Override
    protected void submit() {
        PlatformDataGen.submitItem(id, model, tags, lang);
    }
}
