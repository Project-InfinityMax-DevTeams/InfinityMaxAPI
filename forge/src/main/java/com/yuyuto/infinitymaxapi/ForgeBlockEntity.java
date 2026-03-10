package com.yuyuto.infinitymaxapi;

import com.yuyuto.infinitymaxapi.api.behavior.BehaviorBindingType;
import com.yuyuto.infinitymaxapi.api.behavior.BehaviorContext;
import com.yuyuto.infinitymaxapi.api.registry.BehaviorDefinition;
import com.yuyuto.infinitymaxapi.api.logic.Logic;
import com.yuyuto.infinitymaxapi.api.behavior.Phase;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class ForgeBlockEntity extends BlockEntity {

    private final List<BehaviorDefinition> behaviors;
    private final String blockId;

    public ForgeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state,
                            String blockId,
                            List<BehaviorDefinition> behaviors) {

        super(type, pos, state);
        this.blockId = blockId;
        this.behaviors = behaviors;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ForgeBlockEntity be) {

        public String getBlockId() {
            return blockId;
        }

        for (BehaviorDefinition b : be.behaviors) {

            if (b.trigger() == Phase.TICK) {

                BehaviorContext ctx = new BehaviorContext(
                        BehaviorBindingType.BLOCK,
                        be.getBlockId(),
                        b.trigger(),
                        b.meta()
                );

                Logic logic = b.logic();
                logic.execute(ctx, be);
            }
        }
    }

    public void interact(Player player) {

        for (BehaviorDefinition b : behaviors) {

            if (b.trigger() == Phase.INTERACT) {

                BehaviorContext ctx = new BehaviorContext(
                        BehaviorBindingType.BLOCK,
                        getBlockId(),
                        b.trigger(),
                        b.meta()
                );

                Logic logic = b.logic();
                logic.execute(ctx, player);
            }
        }
    }
}