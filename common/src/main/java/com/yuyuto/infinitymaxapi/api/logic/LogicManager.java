package com.yuyuto.infinitymaxapi.api.logic;

import com.yuyuto.infinitymaxapi.api.behavior.BehaviorContext;

public class LogicManager {

    private LogicManager(){}

    public static void run(String logicId, BehaviorContext ctx){
        LogicRegistry.execute(logicId, ctx, null);
    }

    public static void run(String logicId,BehaviorContext ctx, Object payload){
        LogicRegistry.execute(logicId, ctx ,payload);
    }
}
