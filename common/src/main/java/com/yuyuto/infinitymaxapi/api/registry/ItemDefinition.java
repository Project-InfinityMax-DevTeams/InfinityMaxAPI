package com.yuyuto.infinitymaxapi.api.registry;

import java.util.ArrayList;
import java.util.List;

public class ItemDefinition {

    /* itemID */
    private final String id;

    /* Item Template */
    private final Object template;

    /*Value*/
    private int maxStack = 64;
    private int durability = 0;

    /* DataGen */
    private ModelDefinition model;
    private final List<String> tags = new ArrayList<>();

    public ItemDefinition(String id, Object template){
        this.id = id;
        this.template = template;
    }

    public String getId() {
        return id;
    }

    public Object getTemplate() {
        return template;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public void setMaxStack(int maxStack) {
        this.maxStack = maxStack;
    }

    public int getDurability() {
        return durability;
    }

    public int getMaxStack() {
        return maxStack;
    }

    public ModelDefinition getModel() {
        return model;
    }

    public void setModel(ModelDefinition model) {
        this.model = model;
    }

    public List<String> getTags(){
        return tags;
    }
}
