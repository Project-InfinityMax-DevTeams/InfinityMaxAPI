package com.yuyuto.infinitymaxapi.api.logic;

import com.yuyuto.infinitymaxapi.api.behavior.BehaviorContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LogicRegistry {

    private static final Map<Logic, Logic> LOGICS = new ConcurrentHashMap<>();

    private LogicRegistry(){}

    // Logic登録
    public static void register(Logic logic){
        LOGICS.put(logic, logic);
    }

    // Logic取得
    public static Logic get(Logic logic){
        return LOGICS.get(logic);
    }

    // Logic実行
    public static void execute(Logic logic, BehaviorContext ctx, Object payload){

        Logic l = LOGICS.get(logic);

        if(l != null){
            l.execute(ctx, payload);
        }
    }
}