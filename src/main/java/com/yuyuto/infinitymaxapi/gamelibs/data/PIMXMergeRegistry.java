import java.util.HashMap;
import java.util.Map;

public class PIMXMergeRegistry {

    private static final Map<Class<?>, PIMXMergeHandler<?>> handlers
            = new HashMap<>();

    static {
        register(Integer.class, Integer::sum);
        register(Double.class, Double::sum);
        register(String.class, (a, b) -> a + b);
        register(Boolean.class, (a, b) -> a || b);
    }

    public static <T> void register(Class<T> type, PIMXMergeHandler<T> handler){
        handlers.put(type, handler);
    }

    @SuppressWarnings("unchecked")
    public static <T> T merge(Class<T> type, Object oldVal, Object newVal){

        PIMXMergeHandler<T> handler = (PIMXMergeHandler<T>) handlers.get(type);

        if (handler == null){
            throw new UnsupportedOperationException("No merge handler for type: " + type);
        }

        return handler.merge((T) oldVal, (T) newVal);
    }
}