package com.yuyuto.infinitymaxapi.gamelibs.data;

import java.util.HashMap;
import java.util.Map;

/**
 * PIMXデータ本体。
 * 1Owner単位で管理される
 */
public class PIMXData {
    private final String version = "1.0";
    private final PIMXOwner owner;

    //key → entry
    private final Map<String,PIMXEntry<?>> dataMap = new HashMap<>();

    public PIMXData(PIMXOwner owner){
        this.owner = owner;
    }

    public String getVersion(){
        return version;
    }

    public PIMXOwner getOwner(){
        return owner;
    }

    /**
     * データ管理(競合処理は後でEventBusと連携)
     */
    public <T> void registryEntry(PIMXEntry<T> entry){
        dataMap.put(entry.getKey(), entry);
    }

    /**
     * 型安全に取得
     */
    @SuppressWarnings("unchecked")
    public <T> T getValue(String key,Class<T> type){
        PIMXEntry<?> entry = dataMap.get(key);

        if (entry == null){
            throw new IllegalArgumentException("key not found:" + key);
        }

        if (!entry.getType().equals(type)) {
            throw new IllegalArgumentException("Type mismatch for key:" + key);
        }

        return (T) entry.getValue();
    }
}
