public abstract class BaseGen<T extends BaseGen<T>> {

    protected final String id;

    protected BaseGen(String id) {
        this.id = id;
    }

    protected abstract void submit();

    @SuppressWarnings("unchecked")
    protected T self() {
        return (T) this;
    }

    public void end() {
        submit();
    }
}