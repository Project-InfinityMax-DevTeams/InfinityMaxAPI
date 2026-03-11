package com.yuyuto.infinitymaxapi.api.logic;

import com.yuyuto.infinitymaxapi.api.behavior.BehaviorContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LogicRegistry {

    private static final Map<String, Logic> LOGICS = new ConcurrentHashMap<>();

    private LogicRegistry(){}

    // Logic登録
    public static void register(Logic logic){
        LOGICS.put(logic.id(), logic);
    }

    // Logic取得
    public static Logic get(String id){
        return LOGICS.get(id);
    }

    // Logic実行
    public static void execute(String id, BehaviorContext ctx, Object payload){

        Logic l = LOGICS.get(id);

        if(l != null){
            l.execute(ctx, payload);
        }
    }
}