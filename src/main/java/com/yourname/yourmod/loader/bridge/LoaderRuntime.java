package com.yourname.yourmod.loader.bridge;

import com.yourname.yourmod.api.system.SystemRuntime;
import com.yourname.yourmod.api.system.event.EventBridge;
import com.yourname.yourmod.api.system.network.NetworkChannel;
import com.yourname.yourmod.api.system.save.InMemorySaveManager;
import com.yourname.yourmod.api.system.save.SaveManager;
import com.yourname.yourmod.api.system.state.InMemoryStateManager;
import com.yourname.yourmod.api.system.state.StateManager;
import com.yourname.yourmod.api.system.tick.SimpleTickScheduler;
import com.yourname.yourmod.api.system.tick.TickScheduler;

/**
 * 共通API層に公開するランタイム実装。
 * プラットフォーム依存実装への委譲はloader層で差し替える。
 */
public final class LoaderRuntime implements SystemRuntime {

    private final StateManager stateManager = new InMemoryStateManager();
    private final NetworkChannel networkChannel = new PlatformNetworkChannel();
    private final TickScheduler tickScheduler = new SimpleTickScheduler();
    private final SaveManager saveManager = new InMemorySaveManager();
    private final EventBridge eventBridge = new PlatformEventBridge();

    @Override
    public StateManager stateManager() {
        return stateManager;
    }

    @Override
    public NetworkChannel networkChannel() {
        return networkChannel;
    }

    @Override
    public TickScheduler tickScheduler() {
        return tickScheduler;
    }

    @Override
    public SaveManager saveManager() {
        return saveManager;
    }

    @Override
    public EventBridge eventBridge() {
        return eventBridge;
    }
}
