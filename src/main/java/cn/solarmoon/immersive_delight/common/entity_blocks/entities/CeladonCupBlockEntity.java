package cn.solarmoon.immersive_delight.common.entity_blocks.entities;

import cn.solarmoon.immersive_delight.common.IMEntityBlocks;
import cn.solarmoon.immersive_delight.api.common.entity_block.entities.BaseContainerTankBlockEntity;
import cn.solarmoon.immersive_delight.init.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class CeladonCupBlockEntity extends BaseContainerTankBlockEntity {

    public CeladonCupBlockEntity(BlockPos pos, BlockState state) {
        super(IMEntityBlocks.CELADON_CUP_ENTITY.get(), pos, state, 250, 1, 1);
    }

}