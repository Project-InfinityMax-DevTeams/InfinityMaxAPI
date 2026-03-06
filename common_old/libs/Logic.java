package com.yuyuto.infinitymaxapi.api.libs;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Logic DSL の Java 呼び出し入口。
 */
public final class Logic {

    private Logic() {
    }

    public static void connect(Consumer<LogicScope> block) {
        LogicApi.logicJava(Objects.requireNonNull(block, "block"));
    }
}
