package cn.solarmoon.immersive_delight.common.entity_blocks;

import cn.solarmoon.immersive_delight.common.IMEntityBlocks;
import cn.solarmoon.immersive_delight.api.common.entity_block.specific.AbstractCupEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

/**
 * 青瓷杯方块
 */
public class CeladonCupEntityBlock extends AbstractCupEntityBlock {

    public CeladonCupEntityBlock() {
        super(Block.Properties.of()
                .sound(SoundType.GLASS)
        );
    }

    /**
     * 碰撞箱
     */
    protected static final VoxelShape SHAPE = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 4.5D, 10.0D);
    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    @Override
    public BlockEntityType<?> getBlockEntityType() {
        return IMEntityBlocks.CELADON_CUP_ENTITY.get();
    }
}