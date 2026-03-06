package com.yuyuto.infinitymaxapi.api.registry;

import java.util.ArrayList;
import java.util.List;

public class ItemDefinition {

    /* itemID */
    private final String id;

    /* Item Template */
    private final T template;

    /* DataGen */
    private ModelDefinition model;
    private final List<String> tags = new ArrayList<>();

    public ItemDefinition(String id, T template){
        this.id = id;
        this.template = template;
    }

    public String getId() {
        return id;
    }

    public T getTemplate() {
        return template;
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
