package com.yuyuto.infinitymaxapi.api.libs.datagen;

import com.yuyuto.infinitymaxapi.api.platform.PlatformDataGen;
import java.util.ArrayList;
import java.util.List;

public final class BlockGen extends BaseGen<BlockGen> {

    private Model model;
    private Loot loot;
    private final List<TagKey<?>> tags = new ArrayList<>();

    public BlockGen(String id) {
        super(id);
    }

    public BlockGen model(Model model) {
        this.model = model;
        return this;
    }

    public BlockGen loot(Loot loot) {
        this.loot = loot;
        return this;
    }

    public BlockGen tag(TagKey<?> tag) {
        tags.add(tag);
        return this;
    }

    @Override
    protected void submit() {
        PlatformDataGen.submitBlock(id, model, loot, tags);
    }
}
