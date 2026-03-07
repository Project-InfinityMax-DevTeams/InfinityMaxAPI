package com.yuyuto.infinitymaxapi;

import com.yuyuto.infinitymaxapi.api.registry.ItemDefinition;
import com.yuyuto.infinitymaxapi.api.registry.ItemRegister;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class FabricItemRegister implements ItemRegister {

    private final String modid;

    public FabricItemRegister(String modid){
        this.modid = modid;
    }

    @Override
    public void register(ItemDefinition def){
        Item.Settings props = new Item.Settings().maxCount(def.maxStack);

        if (def.durability > 0){
            props.maxDamage(def.durability);
        }

        Item item = new Item(props);

        Registry.register(Registries.ITEM,new Identifier(modid, def.getId()),item);
    }
}
