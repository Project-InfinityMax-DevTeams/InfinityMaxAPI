package com.yourname.yourmod.api.system.save;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * プラットフォーム保存層の前段に置く共通集約実装。
 */
public final class InMemorySaveManager implements SaveManager {

    private final Map<String, SaveSection> sections = new LinkedHashMap<>();

    @Override
    public void register(String sectionId, SaveSection section) {
        sections.put(sectionId, section);
    }

    @Override
    public Map<String, Object> saveAll() {
        Map<String, Object> data = new LinkedHashMap<>();
        for (Map.Entry<String, SaveSection> entry : sections.entrySet()) {
            data.put(entry.getKey(), entry.getValue().save());
        }
        return data;
    }

    @Override
    public void loadAll(Map<String, Object> rawData) {
        for (Map.Entry<String, Object> entry : rawData.entrySet()) {
            SaveSection section = sections.get(entry.getKey());
            if (section != null) {
                section.load(entry.getValue());
            }
        }
    }
}
