package com.yourname.yourmod.api.lifecycle;

/**
 * ライフサイクルごとの処理フック
 */
@FunctionalInterface
public interface LifecycleHook {
    void run();
}