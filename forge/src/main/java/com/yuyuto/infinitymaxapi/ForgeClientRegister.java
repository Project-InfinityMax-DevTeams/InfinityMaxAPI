package com.yuyuto.infinitymaxapi;

import com.yuyuto.infinitymaxapi.api.registry.BlockDefinition;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class ForgeClientRegister {

    private final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES;
    private final List<BlockDefinition> definitions;

    public ForgeClientRegister(String modid, IEventBus bus, List<BlockDefinition> definitions){

        this.definitions = definitions;

        BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, modid);

        BLOCK_ENTITY_TYPES.register(bus);
        bus.register(this);
    }

    @SubscribeEvent
    public void registerRenderers(EntityRenderersEvent.RegisterRenderers event){

        for(BlockDefinition def : definitions){

            if (def.hasRenderer()){

                BlockEntityType<?> type = ForgeRegistries.BLOCK_ENTITY_TYPES.getValue(new ResourceLocation(def.getId()));

                var factory = RendererRegistry.get(def.getRenderer());

                event.registerBlockEntityRenderer(type, factory);
            }
        }
    }
}