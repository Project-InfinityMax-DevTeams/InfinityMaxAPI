package com.yuyuto.infinitymaxapi.gamelibs.data;

public class PIMXEntry<T> {
    private final String key;
    private final Class<T> type;
    private T value;
    private final PIMXSync sync;

    public PIMXEntry(String key, Class<T> type, T value, PIMXSync sync){
        this.key = key;
        this.type = type;
        this.value = value;
        this.sync = sync;
    }

    public String getKey(){
        return key;
    }

    public Class<T> getType(){
        return type;
    }

    public T getValue(){
        return value;
    }

    public void setValue(T value){
        //型安全チェック
        if (!type.isInstance(value)){
            throw new IllegalArgumentException("Invalid type for key:" + key);
        }
        this.value = value;
    }

    public PIMXSync getSync(){
        return sync;
    }

    @SuppressWarnings("unchecked")
    public static <T> PIMXEntry<T> create(String key,Class<?> clazz,Object value,PIMXSync sync){
        return new PIMXEntry<>(key,(Class<T>) clazz,(T) value,sync);
    }
}
