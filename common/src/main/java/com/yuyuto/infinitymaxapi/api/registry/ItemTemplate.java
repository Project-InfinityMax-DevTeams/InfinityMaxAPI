package com.yuyuto.infinitymaxapi.api.registry;

public class ItemTemplate {

    private final String id;

    private ItemTemplate(String id){
        this.id = id;
    }

    public static ItemTemplate of(String id){
        return new ItemTemplate(id);
    }

    public String getId(){
        return id;
    }
}
