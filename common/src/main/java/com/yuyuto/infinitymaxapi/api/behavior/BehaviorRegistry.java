package com.yuyuto.infinitymaxapi.api.behavior;

import java.util.ArrayList;
import java.util.List;

public final class BehaviorRegistry {
    private static final List<Behavior> BEHAVIORS = new ArrayList<>();

    private BehaviorRegistry(){}

    public static void register(Behavior behavior){
        BEHAVIORS.add(behavior);
    }

    public static List<Behavior> all(){
        return List.copyOf(BEHAVIORS);
    }
}
