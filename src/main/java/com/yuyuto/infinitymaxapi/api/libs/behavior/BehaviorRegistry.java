package com.yuyuto.infinitymaxapi.api.libs.behavior;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 振る舞い接続DSLの内部登録層。
 *
 * <p>Java Core 側に保持されるため、Kotlin DSL はここへ定義を渡すだけに留まる。</p>
 */
public final class BehaviorRegistry {

    private static final List<BehaviorBinding> BINDINGS = new ArrayList<>();
    private static final List<PacketBehaviorBinding<?>> PACKET_BINDINGS = new ArrayList<>();

    private BehaviorRegistry() {}

    /** 接続定義を登録する。 */
    public static void register(BehaviorBinding binding) {
        BINDINGS.add(binding);
    }

    /** パケット接続定義を登録する。 */
    public static <T> void registerPacket(PacketBehaviorBinding<T> binding) {
        PACKET_BINDINGS.add(binding);
    }

    /** 参照用: 指定種別の接続一覧を返す。 */
    public static List<BehaviorBinding> byType(BehaviorBindingType type) {
        return BINDINGS.stream().filter(it -> it.type() == type).collect(Collectors.toUnmodifiableList());
    }

    /** 参照用: 全接続一覧を返す。 */
    public static List<BehaviorBinding> all() {
        return Collections.unmodifiableList(BINDINGS);
    }

    /** 宣言済み接続を即時実行するヘルパ。 */
    public static void execute(BehaviorBindingType type, String targetId) {
        BINDINGS.stream()
                .filter(it -> it.type() == type && Objects.equals(it.targetId(), targetId))
                .forEach(BehaviorBinding::execute);
    }

    /** 宣言済みパケット接続を実行するヘルパ。 */
    @SuppressWarnings("unchecked")
    public static <T> void executePacket(String targetId, T payload) {
        PACKET_BINDINGS.stream()
                .filter(it -> Objects.equals(it.targetId(), targetId))
                .forEach(it -> ((PacketBehaviorBinding<T>) it).execute(payload));
    }
}
