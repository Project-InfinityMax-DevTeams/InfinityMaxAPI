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
        // ここは次のステップで実装
        return new HashMap<>();
    }
}