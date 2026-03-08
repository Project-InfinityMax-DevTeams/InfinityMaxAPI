package com.yuyuto.infinitymaxapi.api.registry;

import java.util.List;

public class BlockEntityDefinition {

    private final String id;
    private final List<BehaviorDefinition> behaviors;

    public BlockEntityDefinition(String id, List<BehaviorDefinition> behaviors){
        this.id = id;
        this.behaviors = behaviors;
    }

    public String getId(){
        return id;
    }

    public List<BehaviorDefinition> getBehaviors(){
        return behaviors;
    }
}