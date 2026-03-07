package com.yuyuto.infinitymaxapi.api.registry;

import java.util.ArrayList;
import java.util.List;

public class EntityDefinition {

    /* EntityID */
    private final String id;

    /* Entity Template */
    private final Object template;

    /* Value */
    private final MobCategory category = MobCategory.AMBIENT;
    private float height = 2.8f;
    private float width = 1.0f;
    private int updateInterval = 1;
    private boolean sendVelocityUpdates = true;
    private boolean fireImmune = false;
    private boolean summonable = false;
    private boolean saveable = false;

    /* DataGenコード */
    private ModelDefinition model;
    private LootDefinition loot;
    private final List<String> tags = new ArrayList<>();
    private final List<BehaviorDefinition> behaviors = new ArrayList<>();

    public EntityDefinition(String id, Object template){
        this.id = id;
        this.template = template;
    }

    public String getId(){
        return id;
    }

    public Object getTemplate() {
        return template;
    }

    public float getHeight(){
        return height;
    }

    public float getWidth(){
        return width;
    }

    public void setHeight(float height){
        this.height = height;
    }

    public void setWidth(float width){
        this.width = width;
    }

    public int getUpdateInterval(){
        return updateInterval;
    }

    public void setUpdateInterval(int updateInterval){
        this.updateInterval = updateInterval;
    }

    public boolean isFireImmune() {
        return fireImmune;
    }

    public boolean isSummonable() {
        return summonable;
    }

    public boolean isSaveable() {
        return saveable;
    }

    public boolean isSendVelocityUpdates() {
        return sendVelocityUpdates;
    }

    public void setFireImmune(boolean fireImmune) {
        this.fireImmune = fireImmune;
    }

    public void setSummonable(boolean summonable) {
        this.summonable = summonable;
    }

    public void setSaveable(boolean saveable) {
        this.saveable = saveable;
    }

    public void setSendVelocityUpdates(boolean sendVelocityUpdates) {
        this.sendVelocityUpdates = sendVelocityUpdates;
    }

    public ModelDefinition getModel(){
        return model;
    }

    public void setModel(ModelDefinition model) {
        this.model = model;
    }

    public LootDefinition getLoot(){
        return loot;
    }

    public void setLoot(LootDefinition loot){
        this.loot = loot;
    }

    public List<String> getTags(){
        return tags;
    }

    public void addBehavior(BehaviorDefinition behavior){
        behaviors.add(behavior);
    }

    public List<BehaviorDefinition> getBehaviors(){
        return behaviors;
    }
}
