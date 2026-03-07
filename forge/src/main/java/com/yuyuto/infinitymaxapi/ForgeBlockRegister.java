package com.yuyuto.infinitymaxapi;

import com.yuyuto.infinitymaxapi.api.registry.BlockDefinition;
import com.yuyuto.infinitymaxapi.api.registry.BlockRegister;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ForgeBlockRegister implements BlockRegister {

    private final DeferredRegister<Block> BLOCKS;

    public ForgeBlockRegister(String modid, IEventBus bus){
        BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, modid);
        BLOCKS.register(bus);
    }

    @Override
    public void register(BlockDefinition def){
        BLOCKS.register(def.getId(),() -> new Block(BlockBehaviour.Properties.of().strength(def.getHardness(), def.getResistance())));
    }
}
