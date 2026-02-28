package com.yuyuto.infinitymaxapi.api.libs.datagen;

import com.yuyuto.infinitymaxapi.api.platform.PlatformDataGen;
import java.util.ArrayList;
import java.util.List;

public final class ItemGen extends BaseGen<ItemGen> {

    private Model model;
    private String lang;
    private final List<TagKey<?>> tags = new ArrayList<>();

    public ItemGen(String id) {
        super(id);
    }

    public ItemGen model(Model model) {
        this.model = model;
        return this;
    }

    public ItemGen tag(TagKey<?> tag) {
        tags.add(tag);
        return this;
    }

    public ItemGen lang(String name) {
        this.lang = name;
        return this;
    }

    @Override
    protected void submit() {
        PlatformDataGen.submitItem(id, model, tags, lang);
    }
}
