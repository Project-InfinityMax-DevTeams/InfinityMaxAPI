package com.yuyuto.infinitymaxapi;

import com.yuyuto.infinitymaxapi.api.registry.BlockDefinition;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.minecraft.block.entity.BlockEntityType;

import java.util.List;

public class FabricClientRegister {

    public static void registerRenderers(List<BlockDefinition> definitions){
        for(BlockDefinition def : definitions){
            if(def.hasRenderer()){
                BlockEntityType<?> type = FabricRegistry.getBlockEntityType(def.getId());
                var factory = RendererRegistry.get(def.getRenderer());
                BlockEntityRendererRegistry.register(type, factory);
            }
        }
    }
}