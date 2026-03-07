package com.yuyuto.infinitymaxapi;

import com.yuyuto.infinitymaxapi.api.registry.ItemDefinition;
import com.yuyuto.infinitymaxapi.api.registry.ItemRegister;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ForgeItemRegister implements ItemRegister {

    private final DeferredRegister<Item> ITEMS;

    public ForgeItemRegister(String modid, IEventBus bus){
        ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, modid);
        ITEMS.register(bus);
    }

    @Override
    public void register(ItemDefinition def){
        ITEMS.register(def.getId(), () -> {
            Item.Properties props = new Item.Properties().stacksTo(def.getMaxStack());
            if(def.getDurability() > 0){
                props.durability(def.getDurability());
            }
            return new Item(props);
        });
    }
}
