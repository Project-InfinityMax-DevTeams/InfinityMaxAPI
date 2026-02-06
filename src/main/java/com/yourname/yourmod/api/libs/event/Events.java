package com.yourname.yourmod.api.libs.event;

import com.yourname.yourmod.api.event.ModEvent;
import com.yourname.yourmod.api.event.EventPriority;

/**
 * イベントDSLのエントリポイント
 * MOD作者は基本ここしか触らない
 */
public final class Events {

    private Events() {}

    /**
     * 汎用イベント登録
     */
    public static <T extends ModEvent> EventBuilder<T> on(Class<T> eventClass) {
        return new EventBuilder<>(eventClass);
    }

    /**
     * よく使うイベントのショートカット例
     */
    public static EventBuilder<com.yourname.yourmod.api.event.PlayerJoinEvent> playerJoin() {
        return on(com.yourname.yourmod.api.event.PlayerJoinEvent.class);
    }
}