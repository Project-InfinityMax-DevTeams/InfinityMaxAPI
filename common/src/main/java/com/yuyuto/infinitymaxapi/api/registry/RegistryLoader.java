package com.yuyuto.infinitymaxapi.api.registry;

public class RegistryLoader {

    public static void load(RegistryDefinition def){

        var provider = ModRegistryProvider.get();

        //blocks
        for (BlockDefinition block : def.getBlocks().values()) {
            provider.blockRegister().register(block);

            if(block.getModel() != null){
                modelProvider().add(block);
            }
            if(block.getLoot() != null){
                lootProvider().add(block);
            }
        }

        for(ItemDefinition item : def.getItems().values()){
            provider.itemRegister().register(item);

            if(item.getModel() != null){
                provider.modelProvider().add(item);
            }
        }
    }
}
