package com.yuyuto.infinitymaxapi;

import com.yuyuto.infinitymaxapi.api.registry.BlockDefinition;
import com.yuyuto.infinitymaxapi.api.registry.BlockRegister;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.resources.ResourceLocation;

public class ForgeBlockRegister implements BlockRegister {

    private final DeferredRegister<Block> BLOCKS;
    private final DeferredRegister<Item> ITEMS;
    private final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES;

    public ForgeBlockRegister(String modid, IEventBus bus){
        BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, modid);
        ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, modid);
        BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, modid);

        BLOCKS.register(bus);
        ITEMS.register(bus);
        BLOCK_ENTITIES.register(bus);
    }

    @Override
    public void register(BlockDefinition def){

        ResourceLocation templateId = new ResourceLocation(def.getTemplate().getId());
        Block base = ForgeRegistries.BLOCKS.getValue(templateId);
        var block = BLOCKS.register(def.getId(), () -> {
            BlockBehaviour.Properties props = BlockBehaviour.Properties.copy(base).strength(def.getHardness(), def.getResistance());
            return new Block(props);
        });

        ITEMS.register(def.getId(),() -> new BlockItem(block.get(), new Item.Properties()));

        // BehaviorあるならBlockEntity登録
        if(!def.getBehaviors().isEmpty()){
            BLOCK_ENTITIES.register(def.getId(), () -> BlockEntityType.Builder.of((pos,state) -> new ForgeBlockEntity(null,pos,state,def.getId(),def.getBehaviors()),block.get()).build(null));
        }
    }
}