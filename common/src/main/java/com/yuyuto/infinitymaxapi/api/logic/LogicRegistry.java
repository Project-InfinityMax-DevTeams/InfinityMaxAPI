package com.yuyuto.infinitymaxapi.api.logic;


import com.yuyuto.infinitymaxapi.api.behavior.BehaviorContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LogicRegistry {

    private static final Map<String, Logic> LOGICS = new ConcurrentHashMap<>();

    private LogicRegistry(){}

    //Logic登録
    public static void register(Logic logic){
        LOGICS.put(logic.id(), logic);
    }

    //logic取得
    public static Logic get(String id){
        return LOGICS.get(id);
    }

    //logic実行
    public static void execute(String id, BehaviorContext ctx, Object payload){
        Logic logic = LOGICS.get(id);

        if (logic != null){
            logic.execute(ctx, payload);
        }
    }
}
