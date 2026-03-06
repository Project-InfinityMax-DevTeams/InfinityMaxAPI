package com.yuyuto.infinitymaxapi.api.libs.datagen;

import com.yuyuto.infinitymaxapi.api.datagen.BlockDefinition;

import java.util.List;

public final class BlockGen {

    private final BlockDefinition definition;

    public BlockGen(String id){
        this.definition = new BlockDefinition(id);
    }

    public BlockGen hardness(float value){
        definition.setHardness(value);
        return this;
    }

    public BlockGen resistance(float value){
        definition.setResistance(value);
        return this;
    }

    public BlockGen model(ModelDefinition model){
        definition.setModel(model);
        return this;
    }

    public BlockGen tags(List<String> value){
        definition.setTags(value);
        return this;
    }

    public BlockDefinition build(){
        return definition;
    }
}
