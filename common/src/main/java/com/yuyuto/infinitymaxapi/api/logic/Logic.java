package com.yuyuto.infinitymaxapi.api.logic;

import com.yuyuto.infinitymaxapi.api.behavior.BehaviorContext;

public interface Logic {
    String id;
    void execute(BehaviorContext context, Object payload);

}