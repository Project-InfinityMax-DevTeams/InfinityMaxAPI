package com.yourname.yourmod.api.system.save;

import java.util.Map;

public interface SaveManager {

    void register(String sectionId, SaveSection section);

    Map<String, Object> saveAll();

    void loadAll(Map<String, Object> rawData);
}
