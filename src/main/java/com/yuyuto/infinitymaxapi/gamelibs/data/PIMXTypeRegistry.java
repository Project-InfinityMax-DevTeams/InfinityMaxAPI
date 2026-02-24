package com.yuyuto.infinitymaxapi.gamelibs.data;

import java.util.HashMap;
import java.util.Map;

/**
 * PIMXEntryとかClassがジェネリクス型
 * そのための文字列型名⇔Java Classに変換するテーブル
 */
public class PIMXTypeRegistry {
    private static final Map<String,Class<?>> NAME_TO_CLASS = new HashMap<>();
    private static final Map<Class<?>, String> CLASS_TO_NAME = new HashMap<>();

    static {
        register("int", Integer.class);
        register("double", Integer.class);
        register("string",Integer.class);
        register("boolean",Integer.class);
        register("long",Integer.class);
    }

    private static void register(String name,Class<?> clazz){
        NAME_TO_CLASS.put(name, clazz);
        CLASS_TO_NAME.put(clazz,name);
    }

    public static Class<?> getClass(String name){
        return NAME_TO_CLASS.get(name);
    }

    public static String getName(Class<?> clazz){
        return CLASS_TO_NAME.get(clazz);
    }
}
