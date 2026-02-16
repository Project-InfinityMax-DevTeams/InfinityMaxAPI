package com.yourname.yourmod.api.system.example;

import com.yourname.yourmod.api.system.SystemRuntime;
import com.yourname.yourmod.api.system.dsl.ConfigurableSystem;
import com.yourname.yourmod.api.system.dsl.DslDefinition;
import com.yourname.yourmod.api.system.dsl.MapDslDefinition;
import com.yourname.yourmod.api.system.network.Codec;
import com.yourname.yourmod.api.system.network.MessageSpec;
import com.yourname.yourmod.api.system.save.SaveSection;
import com.yourname.yourmod.api.system.state.StateContainer;
import com.yourname.yourmod.api.system.state.StateKey;

import java.util.Map;

/**
 * 実運用サンプル: DSL設定を受け、tick/state/save/event/networkを横断して使う。
 */
public final class HeartbeatSystem implements ConfigurableSystem {

    private static final String SYSTEM_ID = "heartbeat";

    private int intervalTicks = 20;
    private String saveSectionId = "heartbeat";
    private String networkMessageId = "heartbeat/ping";
    private String stateKeyId = "yourmod:heartbeat_count";

    private int heartbeatCount;

    @Override
    public String id() {
        return SYSTEM_ID;
    }

    @Override
    public void configure(DslDefinition definition) {
        if (definition instanceof MapDslDefinition mapDefinition) {
            intervalTicks = Math.max(1, mapDefinition.intValue("intervalTicks", intervalTicks));
            saveSectionId = mapDefinition.stringValue("saveSectionId", saveSectionId);
            networkMessageId = mapDefinition.stringValue("networkMessageId", networkMessageId);
            stateKeyId = mapDefinition.stringValue("stateKeyId", stateKeyId);
            return;
        }

        Map<String, Object> props = definition.properties();
        Object rawInterval = props.get("intervalTicks");
        if (rawInterval instanceof Number number) {
            intervalTicks = Math.max(1, number.intValue());
        }
    }

    @Override
    public void initialize(SystemRuntime runtime) {
        StateKey<Integer> countKey = StateKey.of(stateKeyId, Integer.class);
        StateContainer globalState = runtime.stateManager().getOrCreate(this);

        runtime.saveManager().register(saveSectionId, new SaveSection() {
            @Override
            public Object save() {
                return heartbeatCount;
            }

            @Override
            public void load(Object data) {
                if (data instanceof Number number) {
                    heartbeatCount = number.intValue();
                }
            }
        });

        runtime.networkChannel().register(new MessageSpec<>(
                networkMessageId,
                new Codec<String>() {
                    @Override
                    public String decode(Object buffer) {
                        return String.valueOf(buffer);
                    }

                    @Override
                    public void encode(Object buffer, String payload) {
                    }
                },
                (payload, context) -> {
                }
        ));

        runtime.tickScheduler().scheduleRepeating(intervalTicks, () -> {
            heartbeatCount++;
            globalState.put(countKey, heartbeatCount);
            runtime.eventBridge().emit(new HeartbeatEvent(id(), heartbeatCount));
        });
    }
}
