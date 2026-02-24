public interface PIMXMergeHandler<T> {
    T merge(T oldVal, T newVal);
}