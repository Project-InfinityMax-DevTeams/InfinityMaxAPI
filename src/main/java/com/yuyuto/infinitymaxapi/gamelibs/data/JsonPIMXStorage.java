package com.yuyuto.infinitymaxapi.gamelibs.data;

public class JsonPIMXStorage implements PIMXStorage {

    private final Path filePath;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public JsonPIMXStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public void save(Map<PIMXKey, PIMXData<?>> data) {

        JsonObject root = new JsonObject();

        for (PIMXData<?> entry : data.values()) {

            JsonObject obj = new JsonObject();

            obj.addProperty("type", entry.type().getId());
            obj.addProperty("scope", entry.scope().name());
            obj.addProperty("sync", entry.syncPolicy().name());

            Object raw = entry.type().serialize(entry.value());
            obj.add("value", gson.toJsonTree(raw));

            root.add(entry.key().toString(), obj);
        }

        try (Writer writer = Files.newBufferedWriter(filePath)) {
            gson.toJson(root, writer);
        } catch (IOException e) {
            throw new PIMXException("Failed to save JSON", e);
        }
    }

    @Override
    public Map<PIMXKey, PIMXData<?>> load() {

        Map<PIMXKey, PIMXData<?>> result = new HashMap<>();

        if (!Files.exists(filePath)) {
            return result;
        }

        try (Reader reader = Files.newBufferedReader(filePath)) {

            JsonObject root = gson.fromJson(reader, JsonObject.class);

            for (Map.Entry<String, JsonElement> entry : root.entrySet()) {

                String keyString = entry.getKey();
                JsonObject obj = entry.getValue().getAsJsonObject();

                String typeId = obj.get("type").getAsString();
                String scopeName = obj.get("scope").getAsString();
                String syncName = obj.get("sync").getAsString();

                JsonElement valueElement = obj.get("value");

                PIMXType<?> type = PIMXTypes.get(typeId);

                Object raw = gson.fromJson(valueElement, Object.class);
                Object value = type.deserialize(raw);

                PIMXKey key = PIMXKey.fromString(keyString);
                PIMXScope scope = PIMXScope.valueOf(scopeName);
                PIMXSyncPolicy sync = PIMXSyncPolicy.valueOf(syncName);

                PIMXData<?> data =
                        new PIMXData<>(key, type, scope, sync, value);

                result.put(key, data);
            }

        } catch (IOException e) {
            throw new PIMXException("Failed to load JSON", e);
        }

        return result;
    }
}