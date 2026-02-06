package loader.forge;

import core.api.platform.PlatformDataGen;

public final class ForgeDataGenImpl implements PlatformDataGen {

    @Override
    public void block(String id, Model model, Loot loot, List<TagKey<?>> tags) {
        // Forge DataGenerator / BlockStateProvider / ModelProvider を呼ぶ
    }

    @Override
    public void item(String id, Model model, List<TagKey<?>> tags, String lang) {
        // Forge ItemModelProvider / LanguageProvider
    }

    @Override
    public void entity(String id, Loot loot, String lang) {
        // Forge LootTableProvider / LanguageProvider
    }
}
