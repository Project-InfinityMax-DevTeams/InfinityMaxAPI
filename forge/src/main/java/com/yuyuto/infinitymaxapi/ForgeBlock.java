package com.yuyuto.infinitymaxapi;

import com.yuyuto.infinitymaxapi.api.registry.BlockDefinition;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.Level;

public class ForgeBlock extends Block implements EntityBlock {

    private final BlockDefinition definition;

    public ForgeBlock(BlockBehaviour.Properties props, BlockDefinition def){
        super(props);
        this.definition = def;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {

        if(definition.getBehaviors().isEmpty())
            return null;

        return new ForgeBlockEntity(pos, state, definition.getBehaviors());
    }
}