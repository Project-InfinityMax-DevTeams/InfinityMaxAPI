package com.yourname.yourmod.api.libs.internal;

import com.yourname.yourmod.api.libs.ModRegistries;

public final class ItemBuilder<T, TAB> {

    private final String id;
    private T template;
    private int stack = 64;
    private TAB tab;
    private int durability = 0;

    public ItemBuilder(String id) {
        this.id = id;
    }

    public ItemBuilder<T, TAB> stack(int size) {
        this.stack = size;
        return this;
    }

    public ItemBuilder<T, TAB> tab(TAB tab) {
        this.tab = tab;
        return this;
    }

    public ItemBuilder<T, TAB> durability(int value) {
        this.durability = value;
        return this;
    }

    public ItemBuilder<T, TAB> template(T template) {
        this.template = template;
        return this;
    }

    public T build() {
        if (template == null) {
            throw new IllegalStateException("Template required");
        }

        ModRegistries.registerItem(id, template);
        return template;
    }
}