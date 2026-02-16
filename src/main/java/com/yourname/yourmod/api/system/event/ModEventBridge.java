package com.yourname.yourmod.api.system.event;

import com.yourname.yourmod.api.event.ModEvent;
import com.yourname.yourmod.api.event.ModEventBus;

/**
 * 共通イベントバスへ中継する標準実装。
 */
public final class ModEventBridge implements EventBridge {

    @Override
    public void registerForwarders() {
        // 実際のイベント購読はloader層でこのブリッジを呼び出して登録する。
    }

    @Override
    public void emit(ModEvent event) {
        ModEventBus.post(event);
    }
}
