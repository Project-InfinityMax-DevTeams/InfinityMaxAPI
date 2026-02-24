package com.yuyuto.infinitymaxapi.gamelibs.data;

import com.google.gson.*;
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

    public JsonObject toJson(){
        JsonObject root = new JsonObject();

        root.addProperty("version", version);
        root.addProperty("scope", owner.scope().name());
        root.addProperty("owner", owner.identifier());

        JsonObject dataObject = new JsonObject();

        for (PIMXEntry<?> entry : dataMap.values()){

            JsonObject entryObject = new JsonObject();

            // type
            String typeName = PIMXTypeRegistry.getName(entry.getType());
            entryObject.addProperty("type", typeName);

            // value
            entryObject.add("value", new Gson().toJsonTree(entry.getValue()));

            // sync
            JsonObject syncObject = new JsonObject();
            syncObject.addProperty("apply", entry.getSync().applyPolicy().name());
            syncObject.addProperty("conflict", entry.getSync().conflictPolicy().name());

            entryObject.add("sync", syncObject);

            dataObject.add(entry.getKey(), entryObject);
        }

        root.add("data", dataObject);

        return root;
    }

    public static PIMXData fromJson(JsonObject root){

        String version = root.get("version").getAsString();
        PIMXScope scope = PIMXScope.valueOf(root.get("scope").getAsString());
        String identifier = root.get("owner").getAsString();

        PIMXOwner owner = new PIMXOwner(scope, identifier);
        PIMXData pimxData = new PIMXData(owner);

        JsonObject dataObject = root.getAsJsonObject("data");

        for (String key : dataObject.keySet()){

            JsonObject entryObject = dataObject.getAsJsonObject(key);

            // type
            String typeName = entryObject.get("type").getAsString();
            Class<?> clazz = PIMXTypeRegistry.getClass(typeName);

            // value
            Object value = new Gson().fromJson(entryObject.get("value"), clazz);

            // sync
            JsonObject syncObject = entryObject.getAsJsonObject("sync");
            ApplyPolicy apply = ApplyPolicy.valueOf(syncObject.get("apply").getAsString());
            ConflictPolicy conflict = ConflictPolicy.valueOf(syncObject.get("conflict").getAsString());

            PIMXSync sync = new PIMXSync(apply, conflict);

            PIMXEntry<?> entry = new PIMXEntry<>(key, clazz, value, sync);

            pimxData.registryEntry(entry);
        }

        return pimxData;
    }
}
