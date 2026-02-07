package com.yourname.yourmod.api.lifecycle;

import java.util.*;

/**
 * MOD ライフサイクル処理の登録管理
 */
public final class LifecycleRegistry {

    private static final Map<ModLifecycle, List<LifecycleHook>> HOOKS = new EnumMap<>(ModLifecycle.class);

    static {
        for (ModLifecycle stage : ModLifecycle.values()) {
            HOOKS.put(stage, new ArrayList<>());
        }
    }

    private LifecycleRegistry() {}

    /** ライフサイクル処理を登録 */
    public static void register(ModLifecycle stage, LifecycleHook hook) {
        HOOKS.get(stage).add(hook);
    }

    /** Loader 側から呼ばれる実行ポイント */
    public static void fire(ModLifecycle stage) {
        for (LifecycleHook hook : HOOKS.get(stage)) {
            hook.run();
        }
    }
}