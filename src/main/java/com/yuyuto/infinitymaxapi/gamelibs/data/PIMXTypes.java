package com.yuyuto.infinitymaxapi.gamelibs.data;

public final class PIMXTypes {

    private static final Map<String, PIMXType<?>> TYPES = new HashMap<>();

    public static final PIMXType<Integer> INTEGER = register(new IntegerType());
    public static final PIMXType<String> STRING = register(new StringType());

    private static <T> PIMXType<T> register(PIMXType<T> type) {
        TYPES.put(type.getId(), type);
        return type;
    }

    @SuppressWarnings("unchecked")
    public static <T> PIMXType<T> get(String id) {
        PIMXType<?> type = TYPES.get(id);
        if (type == null) {
            throw new PIMXException("Unknown type id: " + id);
        }
        return (PIMXType<T>) type;
    }

    private PIMXTypes() {}
}