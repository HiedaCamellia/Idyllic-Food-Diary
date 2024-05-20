package cn.solarmoon.idyllic_food_diary.api.common.block.cookware;

import cn.solarmoon.idyllic_food_diary.core.common.block_entity.SteamerBaseBlockEntity;
import cn.solarmoon.solarmoon_core.api.common.block.entity_block.BasicEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

/**
 * 蒸笼底座，能蓄水，把水烧开，开水会在热源上逐渐消耗
 */
public abstract class AbstractSteamerBaseEntityBlock extends BasicEntityBlock {

    public AbstractSteamerBaseEntityBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
        SteamerBaseBlockEntity steamerBase = (SteamerBaseBlockEntity) level.getBlockEntity(pos);
        if (steamerBase == null) return InteractionResult.PASS;
        if (steamerBase.putFluid(player, hand, false)) {
            level.playSound(null, pos, SoundEvents.BUCKET_FILL, SoundSource.PLAYERS);
            steamerBase.setChanged();
            return InteractionResult.SUCCESS;
        }
        if (steamerBase.takeFluid(player, hand, false)) {
            level.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.PLAYERS);
            steamerBase.setChanged();
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        super.tick(level, pos, state, blockEntity);
        SteamerBaseBlockEntity steamerBase = (SteamerBaseBlockEntity) blockEntity;
        steamerBase.tryBoilWater();
        steamerBase.tryDrainHotFluid();
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);
        SteamerBaseBlockEntity base = (SteamerBaseBlockEntity) level.getBlockEntity(pos);
        if (base == null) return;
        if (base.isEvaporating()) {
            level.addAlwaysVisibleParticle(ParticleTypes.CLOUD, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 0, 0.1, 0);
        } else if (base.isBoiling()) {
            if (random.nextInt(10) < 2) level.addAlwaysVisibleParticle(ParticleTypes.CLOUD, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 0, 0.1, 0);
        }
    }

}