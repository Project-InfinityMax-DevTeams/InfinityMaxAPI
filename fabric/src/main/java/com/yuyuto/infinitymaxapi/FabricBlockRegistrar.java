package com.yuyuto.infinitymaxapi;

import com.yuyuto.infinitymaxapi.api.registry.BlockDefinition;
import com.yuyuto.infinitymaxapi.api.registry.BlockRegistrar;

public class FabricBlockRegistrar implements BlockRegistrar {

    private final String modid;

    public FabricBlockRegistrar(String modid){
        this.modid = modid;
    }

    @Override
    public void register(BlockDefinition def){
        Block block = new Block(FabricBlockSettings.create().strength(def.getHardness(),def.getResistance()));
    }
    Registry.register(Registries.BLOCK,new Identifier(modid, def.getId()),block);
}
