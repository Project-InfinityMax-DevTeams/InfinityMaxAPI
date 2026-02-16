package com.yourname.yourmod.api.system;

import com.yourname.yourmod.api.system.event.EventBridge;
import com.yourname.yourmod.api.system.network.NetworkChannel;
import com.yourname.yourmod.api.system.save.SaveManager;
import com.yourname.yourmod.api.system.state.StateManager;
import com.yourname.yourmod.api.system.tick.TickScheduler;

/**
 * API層から利用する共通基盤へのアクセスポイント。
 */
public interface SystemRuntime {

    StateManager stateManager();

    NetworkChannel networkChannel();

    TickScheduler tickScheduler();

    SaveManager saveManager();

    EventBridge eventBridge();
}
