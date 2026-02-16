package com.yourname.yourmod.api.system.state;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * プラットフォーム非依存の簡易実装。
 */
public final class InMemoryStateManager implements StateManager {

    private final Map<Object, StateContainer> containers = new ConcurrentHashMap<>();

    @Override
    public StateContainer attach(Object owner) {
        StateContainer container = new StateContainer();
        containers.put(owner, container);
        return container;
    }

    @Override
    public StateContainer getOrCreate(Object owner) {
        return containers.computeIfAbsent(owner, key -> new StateContainer());
    }

    @Override
    public void detach(Object owner) {
        containers.remove(owner);
    }
}
