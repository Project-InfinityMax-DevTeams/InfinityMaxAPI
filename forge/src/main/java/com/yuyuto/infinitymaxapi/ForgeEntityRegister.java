package com.yuyuto.infinitymaxapi;

import com.yuyuto.infinitymaxapi.api.registry.EntityDefinition;
import com.yuyuto.infinitymaxapi.api.registry.EntityRegister;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ForgeEntityRegister implements EntityRegister {

    private final DeferredRegister<EntityType<?>> ENTITIES;

    public ForgeEntityRegister(String modid, IEventBus bus){
        ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, modid);
        ENTITIES.register(bus);
    }

    @Override
    public void register(EntityDefinition def){

        ENTITIES.register(def.getId(), () -> {

            EntityType.EntityFactory<?> factory = (EntityType.EntityFactory<?>) def.getTemplate();
            EntityType.Builder<?> builder = EntityType.Builder.of(factory, MobCategory.AMBIENT).sized(def.getWidth(), def.getHeight()).updateInterval(def.getUpdateInterval());

            if(def.isFireImmune()){
                builder.fireImmune();
            }

            if(!def.isSaveable()){
                builder.noSave();
            }

            if(!def.isSummonable()){
                builder.noSummon();
            }

            return builder.build(def.getId());
        });
    }
}