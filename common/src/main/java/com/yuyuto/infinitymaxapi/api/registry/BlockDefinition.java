package com.yuyuto.infinitymaxapi.api.registry;

import java.util.*;

public class BlockDefinition {

    /* BlockID*/
    private final String id;

    /* Block Template */
    private final BlockTemplate template;

    /* 硬さ */
    private float hardness = 1.0f;

    /* 爆発耐性 */
    private float resistance = 1.0f;

    /* DataGenコード */
    private ModelDefinition model;
    private LootDefinition loot;
    private final List<String> tags = new ArrayList<>();
    private final List<BehaviorDefinition> behaviors = new ArrayList<>();

    /* レンダリング */
    private String renderer;

    public BlockDefinition(String id,BlockTemplate template){
        this.id = Objects.requireNonNull(id);
        this.template = template;
    }

    public void setRenderer(String renderer){
        this.renderer = renderer;
    }

    public String getId() {
        return id;
    }

    public BlockTemplate getTemplate() {
        return template;
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

    public ModelDefinition getModel() {
        return model;
    }

    public void setModel(ModelDefinition model){
        this.model = model;
    }

    public LootDefinition getLoot() {
        return loot;
    }

    public void setLoot(LootDefinition loot) {
        this.loot = loot;
    }

    public List<String> getTags() {
        return tags;
    }

    public void addBehavior(BehaviorDefinition behavior){
        behaviors.add(behavior);
    }

    public List<BehaviorDefinition> getBehaviors() {
        return behaviors;
    }

    public void addRenderer(String id){
        this.renderer = id;
    }

    public String getRenderer(){
        return renderer;
    }

    public boolean hasRenderer(){
        return renderer != null;
    }
}
