package com.yourname.yourmod.api.event;

import com.yourname.yourmod.loader.Platform;

public final class ModEvents {

    private ModEvents() {}

    public static void init() {
        Platform.get().registerEvents();
    }
}