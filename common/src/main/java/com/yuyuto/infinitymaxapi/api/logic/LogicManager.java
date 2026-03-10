package com.yuyuto.infinitymaxapi.api.logic;

import com.yuyuto.infinitymaxapi.api.behavior.BehaviorContext;

public class LogicManager {

    private LogicManager(){}

    public static void run(Logic logic, BehaviorContext ctx){
        LogicRegistry.execute(logic, ctx, null);
    }

    public static void run(Logic logic,BehaviorContext ctx, Object payload){
        LogicRegistry.execute(logic, ctx ,payload);
    }
}
