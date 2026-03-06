package com.yuyuto.infinitymaxapi;

import com.yuyuto.infinitymaxapi.api.registry.BlockDefinition;
import com.yuyuto.infinitymaxapi.api.registry.BlockRegistrar;

public class ForgeBlockRegistrar implements BlockRegistrar {

    private final DeferredRegister<Block> BLOCKS;

    public ForgeBlockRegistrar(String modid, IEventBus bus){
        BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, modid);
        BLOCKS.register(bus);
    }

    @Override
    public void register(BlockDefinition def){
        BLOCKS.register(def.getId(),() -> new Block(BlockBehaviour.Properties.of().strength(def.getHardness(), def.getResistance())));
    }
}
