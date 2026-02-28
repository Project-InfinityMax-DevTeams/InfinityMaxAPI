package com.yuyuto.infinitymaxapi.loader.forge;

import com.yuyuto.infinitymaxapi.api.libs.datagen.Loot;
import com.yuyuto.infinitymaxapi.api.libs.datagen.Model;
import com.yuyuto.infinitymaxapi.api.libs.datagen.TagKey;
import com.yuyuto.infinitymaxapi.api.platform.PlatformDataGen;
import java.util.List;

public final class ForgeDataGenImpl implements PlatformDataGen.Handler {

    @Override
    public void block(String id, Model model, Loot loot, List<TagKey<?>> tags) {
    }

    @Override
    public void item(String id, Model model, List<TagKey<?>> tags, String lang) {
    }

    @Override
    public void entity(String id, Loot loot, String lang) {
    }
}
