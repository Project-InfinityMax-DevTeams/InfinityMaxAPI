package com.yourname.yourmod.api.libs.internal;

import com.yourname.yourmod.api.libs.ModRegistries;

public final class ItemBuilder {

    private final String id;
    private Object template;
    private int stack = 64;
    private Object tab = null;
    private int durability = 0;

    public ItemBuilder(String id) {
        this.id = id;
    }

    public ItemBuilder stack(int size) {
        this.stack = size;
        return this;
    }

    public ItemBuilder tab(Object tab) {
        this.tab = tab;
        return this;
    }

    public ItemBuilder durability(int value) {
        this.durability = value;
        return this;
    }

    public ItemBuilder template(Object template) {
        this.template = template;
        return this;
    }

    public Object build() {
        Object item = template != null ? template : new Object();
        ModRegistries.registerItem(id, item);
        return item;
    }
}
