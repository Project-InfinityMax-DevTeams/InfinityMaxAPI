package com.yuyuto.infinitymaxapi.api.platform;

import com.yuyuto.infinitymaxapi.api.libs.datagen.Loot;
import com.yuyuto.infinitymaxapi.api.libs.datagen.Model;
import com.yuyuto.infinitymaxapi.api.libs.datagen.TagKey;
import java.util.List;

public final class PlatformDataGen {

    private static Handler handler = new NoopHandler();

    private PlatformDataGen() {}

    public static void setHandler(Handler value) {
        handler = value == null ? new NoopHandler() : value;
    }

    public static void submitBlock(String id, Model model, Loot loot, List<TagKey<?>> tags) {
        handler.block(id, model, loot, tags);
    }

    public static void submitItem(String id, Model model, List<TagKey<?>> tags, String lang) {
        handler.item(id, model, tags, lang);
    }

    public static void submitEntity(String id, Loot loot, String lang) {
        handler.entity(id, loot, lang);
    }

    public interface Handler {
        void block(String id, Model model, Loot loot, List<TagKey<?>> tags);
        void item(String id, Model model, List<TagKey<?>> tags, String lang);
        void entity(String id, Loot loot, String lang);
    }

    private static final class NoopHandler implements Handler {
        @Override
        public void block(String id, Model model, Loot loot, List<TagKey<?>> tags) {}

        @Override
        public void item(String id, Model model, List<TagKey<?>> tags, String lang) {}

        @Override
        public void entity(String id, Loot loot, String lang) {}
    }
}
