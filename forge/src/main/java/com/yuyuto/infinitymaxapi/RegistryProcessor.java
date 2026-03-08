package com.yuyuto.infinitymaxapi;

import com.yuyuto.infinitymaxapi.api.registry.*;

public class RegistryProcessor {
    private final ItemRegister itemRegister;
    private final BlockRegister blockRegister;

    public RegistryProcessor(ItemRegister itemRegister,BlockRegister blockRegister){
        this.itemRegister = itemRegister;
        this.blockRegister = blockRegister;
    }

    public void registerAll(RegistryDefinition def){
        for(ItemDefinition item : def.getItems().values()){
            itemRegister.register(item);
        }

        for (BlockDefinition block : def.getBlocks().values()){
            blockRegister.register(block);
        }

        for(EntityDefinition entity : def.getEntities().values()){
            entityRegister.register(entity);
        }
    }
}
