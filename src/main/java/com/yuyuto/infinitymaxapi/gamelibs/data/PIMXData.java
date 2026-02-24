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
    public <T> void registryEntry(PIMXEntry<T> newEntry){

        PIMXEntry<?> existing = dataMap.get(newEntry.getKey());

        // 既存が無ければそのまま登録
        if (existing == null){
            dataMap.put(newEntry.getKey(), newEntry);
            return;
        }

        // 型が違う場合は即エラー
        if (!existing.getType().equals(newEntry.getType())){
            throw new IllegalStateException("Type conflict for key: " + newEntry.getKey());
        }

        // 競合ポリシー取得
        ConflictPolicy policy = newEntry.getSync().conflictPolicy();

        switch (policy){

            case REPLACE -> {
                dataMap.put(newEntry.getKey(), newEntry);
            }

            case ERROR -> {
                throw new IllegalStateException("Conflict detected for key: " + newEntry.getKey());
            }

            case MERGE -> {
                Object merged = PIMXMergeRegistry.merge(newEntry.getType(), existing.getValue(), newEntry.getValue());
                ((PIMXEntry<Object>) existing).setValue(merged);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private Object mergeValue(PIMXEntry<?> oldEntry, PIMXEntry<?> newEntry){

        Object oldVal = oldEntry.getValue();
        Object newVal = newEntry.getValue();

        if (oldVal instanceof Integer){
            return (Integer) oldVal + (Integer) newVal;
        }

        if (oldVal instanceof Double){
            return (Double) oldVal + (Double) newVal;
        }

        if (oldVal instanceof String){
            return oldVal.toString() + newVal.toString();
        }

        if (oldVal instanceof Boolean){
            return (Boolean) oldVal || (Boolean) newVal;
        }

        throw new UnsupportedOperationException(
                "Merge not supported for type: " + oldEntry.getType()
        );
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

        if (clazz == null) {
            throw new IllegalStateException("Unknown type: " + typeName);
        }

        return (T) entry.getValue();
    }

    //Jsonシリアライズ
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

    //Jsonデシリアライズ
    public static PIMXData fromJson(JsonObject root){

        String version = root.get("version").getAsString();
        PIMXScope scope = PIMXScope.valueOf(root.get("scope").getAsString());
        String identifier = root.get("owner").getAsString();

        PIMXOwner owner = new PIMXOwner(scope, identifier);
        PIMXData pimxData = new PIMXData(owner);

        JsonObject dataObject = root.getAsJsonObject("data");

        if (!"1.0".equals(version)) {
            throw new IllegalStateException("Unsupported PIMX version: " + version);
        }

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

            PIMXEntry<?> entry = PIMXEntry.create(key, clazz, value, sync);

            pimxData.registryEntry(entry);
        }

        return pimxData;
    }
}
