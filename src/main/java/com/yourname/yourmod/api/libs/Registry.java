package com.yourname.yourmod.api.libs;

import com.yourname.yourmod.api.libs.internal.ItemBuilder;
import com.yourname.yourmod.api.libs.internal.BlockBuilder;

public final class Registry {

    private Registry() {}

    public static ItemBuilder item(String id) {
        return new ItemBuilder(id);
    }

    public static BlockBuilder block(String id) {
        return new BlockBuilder(id);
    }
}