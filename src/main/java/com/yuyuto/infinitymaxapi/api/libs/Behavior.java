package com.yuyuto.infinitymaxapi.api.libs;

import java.util.function.Consumer;

/**
 * 振る舞い接続DSLの Java 呼び出し入口。
 *
 * <p>DSLの実体は Kotlin 側にあるため、Java からはこのブリッジを利用する。</p>
 */
public final class Behavior {

    private Behavior() {}

    public static void connect(Consumer<BehaviorScope> block) {
        BehaviorApi.behaviorJava(block);
    }
}
