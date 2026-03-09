package com.yuyuto.infinitymaxapi;

import com.yuyuto.infinitymaxapi.api.registry.BlockDefinition;
import com.yuyuto.infinitymaxapi.api.registry.BlockRegister;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class FabricBlockRegister implements BlockRegister {

    private final String modid;

    public FabricBlockRegister(String modid){
        this.modid = modid;
    }

    @Override
    public void register(BlockDefinition def){

        Block block = new Block(FabricBlockSettings.create().strength(def.getHardness(), def.getResistance()));
        Identifier id = new Identifier(modid, def.getId());

        Registry.register(Registries.BLOCK, id, block);
        Registry.register(Registries.ITEM,id,new BlockItem(block,new Item.Settings()));

        // BehaviorあるならBlockEntity作る
        if(!def.getBehaviors().isEmpty()){
            BlockEntityType<FabricBlockEntity> type =
            Registry.register(Registries.BLOCK_ENTITY_TYPE,id, FabricBlockEntityTypeBuilder.create((pos, state)->new FabricBlockEntity(null,pos,state,def.getBehaviors()),block).build());
        }
    }
}
