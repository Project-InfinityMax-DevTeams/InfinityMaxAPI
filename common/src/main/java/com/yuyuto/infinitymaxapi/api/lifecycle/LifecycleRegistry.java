package com.yuyuto.infinitymaxapi.api.lifecycle;

import java.util.*;

/**
 * MOD 繝ｩ繧､繝輔し繧､繧ｯ繝ｫ蜃ｦ逅・・逋ｻ骭ｲ邂｡逅・
 */
public final class LifecycleRegistry {

    private static final Map<ModLifecycle, List<LifecycleHook>> HOOKS = new EnumMap<>(ModLifecycle.class);

    static {
        for (ModLifecycle stage : ModLifecycle.values()) {
            HOOKS.put(stage, new ArrayList<>());
        }
    }

    private LifecycleRegistry() {}

    /** 繝ｩ繧､繝輔し繧､繧ｯ繝ｫ蜃ｦ逅・ｒ逋ｻ骭ｲ */
    public static void register(ModLifecycle stage, LifecycleHook hook) {
        HOOKS.get(stage).add(hook);
    }

    /** Loader 蛛ｴ縺九ｉ蜻ｼ縺ｰ繧後ｋ螳溯｡後・繧､繝ｳ繝・*/
    public static void fire(ModLifecycle stage) {
        for (LifecycleHook hook : HOOKS.get(stage)) {
            hook.run();
        }
    }
}