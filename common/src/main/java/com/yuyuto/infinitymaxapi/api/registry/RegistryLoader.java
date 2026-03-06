package com.yuyuto.infinitymaxapi.api.registry;

public class RegistryLoader {

    public static void load(RegistryDefinition def){

        var provider = ModRegistryProvider.get();

        for(BlockDefinition<?> block : def.getBlocks().values()){
            provider.registerBlock(
                    block.getId(),
                    block.getTemplate(),
                    block.getHardness(),
                    block.getResistance()
            );

            if(block.getModel() != null){
                modelProvider.add(block);
            }
            if(block.getLoot() != null){
                lootProvider.add(block);
            }
        }

        for(ItemDefinition<?> item : def.getItems().values()){
            provider.registerItem(
                    item.getId(),
                    item.getTemplate()
            );

            if(item.getModel() != null){
                modelprovider.add(item);
            }
        }
    }
}
