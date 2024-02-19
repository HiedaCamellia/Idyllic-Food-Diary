package cn.solarmoon.immersive_delight.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import static cn.solarmoon.solarmoon_core.common.block.entity_block.BasicEntityBlock.FACING;

public class BlockUtil {

    public static void setBlockWithDirection(BlockState originState, BlockState state, Level level, BlockPos pos) {
        if (state.getValues().get(FACING) != null && originState.getValues().get(FACING) != null) {
            level.setBlock(pos, state.setValue(FACING, originState.getValue(FACING)), 3);
        } else level.setBlock(pos, state, 3);
    }

}
