package com.yuyuto.infinitymaxapi.api.registry;

public class EntityTemplate {

    private final String id;

    private EntityTemplate(String id){
        this.id = id;
    }

    public static EntityTemplate of(String id){
        return new EntityTemplate(id);
    }

    public String getId(){
        return id;
    }
}
