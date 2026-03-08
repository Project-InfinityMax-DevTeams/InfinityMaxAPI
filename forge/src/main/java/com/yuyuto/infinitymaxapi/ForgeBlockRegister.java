package com.yuyuto.infinitymaxapi;

import com.yuyuto.infinitymaxapi.api.registry.BlockDefinition;
import com.yuyuto.infinitymaxapi.api.registry.BlockRegister;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ForgeBlockRegister implements BlockRegister {

    private final DeferredRegister<Block> BLOCKS;
    private final DeferredRegister<Item> ITEMS;

    public ForgeBlockRegister(String modid, IEventBus bus){
        BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, modid);
        ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, modid);
        BLOCKS.register(bus);
        ITEMS.register(bus);
    }

    @Override
    public void register(BlockDefinition def){

        ResourceLocation templateId = new ResourceLocation(def.getTemplate().getId());

        Block base = ForgeRegistries.BLOCKS.getValue(templateId);

        var block = BLOCKS.register(def.getId(), () -> {

            BlockBehaviour.Properties props = BlockBehaviour.Properties.copy(base).strength(def.getHardness(), def.getResistance());

                return new Block(props);
            });

        ITEMS.register(def.getId(), () -> new BlockItem(block.get(), new Item.Properties()));
    }
}
