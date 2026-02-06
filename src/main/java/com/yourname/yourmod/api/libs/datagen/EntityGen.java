public final class EntityGen extends BaseGen<EntityGen> {

    private Loot loot;
    private String lang;

    EntityGen(String id) {
        super(id);
    }

    public EntityGen loot(Loot loot) {
        this.loot = loot;
        return this;
    }

    public EntityGen lang(String name) {
        this.lang = name;
        return this;
    }

    @Override
    protected void submit() {
        PlatformDataGen.submitEntity(id, loot, lang);
    }
}