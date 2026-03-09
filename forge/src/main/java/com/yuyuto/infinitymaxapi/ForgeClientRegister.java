package com.yuyuto.infinitymaxapi;

import com.yuyuto.infinitymaxapi.api.registry.BlockDefinition;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class ForgeClientRegister {

    private final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES;
    public ForgeClientRegister(String modid, IEventBus bus){

        BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, modid);

        BLOCK_ENTITY_TYPES.register(bus);
    }

    @SubscribeEvent
    public void registerRenderers(EntityRenderersEvent.RegisterRenderers event, List<BlockDefinition> definitions){
        for(BlockDefinition def : definitions){
            if (def.hasRenderer()){
                BlockEntityType<?> type = BLOCK_ENTITY_TYPES.get(def.getId());
                var factory = RendererRegistry.get(def.getRenderer());
                event.registerBlockEntityRenderer(type, factory);
            }
        }
    }
}
