package com.yuyuto.infinitymaxapi;

import com.yuyuto.infinitymaxapi.api.registry.EntityDefinition;
import com.yuyuto.infinitymaxapi.api.registry.EntityRegister;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class FabricEntityRegister implements EntityRegister {

    private final String modid;

    public FabricEntityRegister(String modid){
        this.modid = modid;
    }

    @Override
    public void register(EntityDefinition def){

        EntityType.EntityFactory<?> factory = (EntityType.EntityFactory<?>) def.getTemplate();

        EntityType<?> type = EntityType.Builder
            .create(factory, SpawnGroup.AMBIENT)
            .setDimensions(def.getWidth(), def.getHeight())
            .maxTrackingRange(32)
            .trackingTickInterval(def.getUpdateInterval())
            .build();

        Registry.register(Registries.ENTITY_TYPE,new Identifier(modid, def.getId()), type);
    }
}