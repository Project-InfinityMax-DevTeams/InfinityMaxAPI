package com.yuyuto.infinitymaxapi;

import com.yuyuto.infinitymaxapi.api.registry.BlockDefinition;
import com.yuyuto.infinitymaxapi.api.registry.BlockRegister;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class FabricBlockRegister implements BlockRegister {

    public FabricBlockRegister(String modid){
    }

    @Override
    public void register(BlockDefinition def){
        Block block = new Block(FabricBlockSettings.create().strength(def.getHardness(),def.getResistance()));
    }
    Registry.register(Registries.BLOCK,new identifier("infiitymax", def.getId()),block);
}
