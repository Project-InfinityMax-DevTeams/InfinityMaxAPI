package com.yuyuto.infinitymaxapi.api.registry;

public class ModRegistryProvider {
    private  final RegistryDefinition registry;

    public ModRegistryProvider(RegistryDefinition registry){
        this.registry = registry;
    }

    public void registerBlocks(BlockDefinition registrar){
        registry.getBlocks().values().forEach(registrar::register);
    }
}
