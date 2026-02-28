package com.yuyuto.infinitymaxapi.api.libs.datagen;

public final class DataGen {

    private DataGen() {}

    public static BlockGen block(String id) {
        return new BlockGen(id);
    }

    public static ItemGen item(String id) {
        return new ItemGen(id);
    }

    public static EntityGen entity(String id) {
        return new EntityGen(id);
    }
}
