package core.api.platform;

import java.util.List;

public interface PlatformDataGen {

    void block(
        String id,
        Model model,
        Loot loot,
        List<TagKey<?>> tags
    );

    void item(
        String id,
        Model model,
        List<TagKey<?>> tags,
        String lang
    );

    void entity(
        String id,
        Loot loot,
        String lang
    );
}