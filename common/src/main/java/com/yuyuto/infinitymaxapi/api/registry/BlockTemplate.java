package com.yuyuto.infinitymaxapi.api.registry;

public class BlockTemplate {

    private final String id;

    private BlockTemplate(String id) {
        this.id = id;
    }

    public static BlockTemplate of(String id){
        return new BlockTemplate(id);
    }

    public String getId(){
        return id;
    }
}