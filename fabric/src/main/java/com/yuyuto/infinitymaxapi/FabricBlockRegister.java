package com.yuyuto.infinitymaxapi;

import com.yuyuto.infinitymaxapi.api.registry.BlockDefinition;
import com.yuyuto.infinitymaxapi.api.registry.BlockRegister;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
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

        Identifier templateId = new Identifier(def.getTemplate().getId());

        Block base = Registries.BLOCK.get(templateId);
        FabricBlockSettings settings = FabricBlockSettings.copyOf(base).strength(def.getHardness(), def.getResistance());
        Block block = new Block(settings);
        Registry.register(Registries.BLOCK,new Identifier(modid, def.getId()),block);
        Registry.register(Registries.ITEM,new Identifier(modid, def.getId()),new BlockItem(block,new Item.Settings()));
    }
}
