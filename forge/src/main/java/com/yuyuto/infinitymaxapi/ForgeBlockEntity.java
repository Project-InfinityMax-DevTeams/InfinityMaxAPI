package com.yuyuto.infinitymaxapi;

import com.yuyuto.infinitymaxapi.api.registry.BehaviorDefinition;
import com.yuyuto.infinitymaxapi.api.logic.Logic;
import com.yuyuto.infinitymaxapi.api.behavior.Phase;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class ForgeBlockEntity extends BlockEntity {

    private final List<BehaviorDefinition> behaviors;

    public ForgeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, List<BehaviorDefinition> behaviors) {
        super(type, pos, state);
        this.behaviors = behaviors;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ForgeBlockEntity be) {

        for (BehaviorDefinition b : be.behaviors) {

            if (b.trigger().equals(Phase.TICK.name())) {

                Logic logic = b.logic();
                logic.execute(level, pos, state);

            }
        }
    }

    public void interact(Player player) {

        for (BehaviorDefinition b : behaviors) {

            if (b.trigger().equals(Phase.INTERACT.name())) {

                Logic logic = b.logic();
                logic.execute(player);

            }
        }
    }
}