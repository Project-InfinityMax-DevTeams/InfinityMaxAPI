package com.yourname.yourmod.loader.bridge;

import com.yourname.yourmod.api.event.ModEvent;
import com.yourname.yourmod.api.event.ModEventBus;
import com.yourname.yourmod.api.system.event.EventBridge;
import com.yourname.yourmod.loader.Platform;

/**
 * loaderイベントを共通イベントバスへ中継する実装。
 */
public final class PlatformEventBridge implements EventBridge {

    @Override
    public void registerForwarders() {
        Platform.get().events().register();
    }

    @Override
    public void emit(ModEvent event) {
        ModEventBus.post(event);
    }
}
