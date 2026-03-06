package com.yuyuto.infinitymaxapi;

public class ForgeBlockRegistrar implements BlockRegistrar {

    @Override
    public void register(BlockDefinition def){
        Block block = new Block(BlockBehaviour.Properties.of().strangth(def.getHardness(), def.getResistance()));
        ForgeRegistries.BLOCKS.register(def.getId(), block);
    }
}
