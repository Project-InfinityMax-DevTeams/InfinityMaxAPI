package com.yuyuto.infinitymaxapi.api.behavior;

import com.yuyuto.infinitymaxapi.api.logic.LogicManager;

public final class BehaviorManager {

    private BehaviorManager(){}

    public static void trigger(
            BehaviorBindingType type,
            String targetId,
            Phase phase
    ){
        for (Behavior behavior : BehaviorRegistry.all()){

            if (behavior.type() != type) continue;
            if (!behavior.targetId().equals(targetId)) continue;
            if (behavior.phase() != phase) continue;

            BehaviorContext context = new BehaviorContext(
                    type,
                    targetId,
                    phase,
                    behavior.metadata()
            );

            LogicManager.run(behavior.logicId(), context);
        }
    }
}
