package com.yuyuto.infinitymaxapi;

import com.yuyuto.infinitymaxapi.api.registry.*;

public class RegistryProcessor {
    private final ItemRegister itemRegister;
    private final BlockRegister blockRegister;
    private final EntityRegister entityRegister;

    public RegistryProcessor(ItemRegister itemRegister,BlockRegister blockRegister, EntityRegister entityRegister){
        this.itemRegister = itemRegister;
        this.blockRegister = blockRegister;
        this.entityRegister = entityRegister;
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
