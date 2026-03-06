package com.yuyuto.infinitymaxapi.api.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BlockDefinition {

    /* BlockID*/
    private final String id;

    /* 硬さ */
    private float hardness = 1.0f;

    /* 爆発耐性 */
    private float resistance = 1.0f;

    /* DataGenコード */
    private ModelDefinition model;
    private LootDifinition loot;
    private List<String> tags = new ArrayList<>();

    public BlockDefinition(String id){
        this.id = Objects.requireNonNull(id);
    }

    public String getId() {
        return id;
    }

    public float getHardness(){
        return hardness;
    }

    public void setHardness(float hardness) {
        this.hardness = hardness;
    }

    public float getResistance() {
        return resistance;
    }

    public void setResistance(float resistance) {
        this.resistance = resistance;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
