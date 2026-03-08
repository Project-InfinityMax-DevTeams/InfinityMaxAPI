package com.yuyuto.infinitymaxapi;

import com.yuyuto.infinitymaxapi.api.registry.BehaviorDefinition;
import com.yuyuto.infinitymaxapi.api.logic.Logic;
import com.yuyuto.infinitymaxapi.api.behavior.Phase;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.BlockState;
import net.minecraft.world.World;
import net.minecraft.entity.player.PlayerEntity;

import java.util.List;

public class FabricBlockEntity extends BlockEntity {

    private final List<BehaviorDefinition> behaviors;

    public FabricBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, List<BehaviorDefinition> behaviors) {
        super(type, pos, state);
        this.behaviors = behaviors;
    }

    public void tick(World world, BlockPos pos, BlockState state) {

        for (BehaviorDefinition b : behaviors) {

            if (b.trigger() == Phase.TICK.name()) {

                Logic logic = b.logic();
                logic.execute(world, pos, state);

            }
        }
    }

    public void interact(PlayerEntity player) {

        for (BehaviorDefinition b : behaviors) {

            if (b.trigger() == Phase.INTERACT.name()) {

                Logic logic = b.logic();
                logic.execute(player);

            }
        }
    }
}