package com.yuyuto.infinitymaxapi.api.registry;

public class ModelDefinition {

    private String parent;
    private String texture;

    public String getParent(){
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }
}
