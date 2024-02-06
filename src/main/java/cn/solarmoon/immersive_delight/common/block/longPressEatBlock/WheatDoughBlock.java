package cn.solarmoon.immersive_delight.common.block.longPressEatBlock;

import cn.solarmoon.immersive_delight.api.common.block.food.BaseLongPressEatFoodBlock;
import cn.solarmoon.immersive_delight.api.network.serializer.ClientPackSerializer;
import cn.solarmoon.immersive_delight.client.particle.vanilla.Wave;
import cn.solarmoon.immersive_delight.common.registry.IMBlocks;
import cn.solarmoon.immersive_delight.compat.farmersdelight.util.FarmersUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.solarmoon.immersive_delight.client.particle.vanilla.Wave.wave;

/**
 * 面团方块
 */
public class WheatDoughBlock extends BaseLongPressEatFoodBlock {

    public static final IntegerProperty FALL_COUNT = IntegerProperty.create("fall_count", 0, 10);

    public WheatDoughBlock() {
        super(Block.Properties
                .copy(Blocks.CAKE)
                .destroyTime(1f));
        this.registerDefaultState(this.getStateDefinition().any().setValue(FALL_COUNT, 0).setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    /**
     * 踩三下或从大于五格处坠落到上面会压成面饼
     * 减免百分之三十坠落伤害
     */
    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        //减免30%掉落伤害
        float damageMultiplier = 0.7F;
        entity.causeFallDamage(fallDistance, damageMultiplier, entity.damageSources().fall());

        //粒子
        ParticleOptions particle = new BlockParticleOption(ParticleTypes.BLOCK, state);
        if(level.isClientSide) {
            wave(particle, pos, 2, 5, 0.5, 0);
        }

        //音效
        level.playSound(null, pos, SoundEvents.WOOL_BREAK, SoundSource.BLOCKS, 2.0F, 1.0F);

        int fallCount = state.getValue(getFallCount());

        //压面
        if (fallDistance > 5 || fallCount >= 2) {
            if (entity instanceof LivingEntity) {
                level.destroyBlock(pos, false);
                level.setBlock(pos, IMBlocks.FLATBREAD_DOUGH.get().defaultBlockState(), 3);
                level.playSound(null, pos, SoundEvents.WOOL_BREAK, SoundSource.BLOCKS, 3.0F, 0.5F);
                if (level.isClientSide) wave(particle, pos, 5, 10, Math.max(fallDistance / 5, 2), 0.8);
            }
        } else level.setBlock(pos, state.setValue(getFallCount(), fallCount + 1), 3);

    }

    public  IntegerProperty getFallCount() {
        return FALL_COUNT;
    }

    /**
     * 碰撞箱
     */
    protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 4.0D, 12.0D);
    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    /**
     * 如果农夫乐事加载，则生成农夫乐事的面团
     */
    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        return FarmersUtil.getDough() == null ? super.getDrops(state, builder) : List.of(FarmersUtil.getDough().getDefaultInstance());
    }

    /**
     * 农夫乐事加载则换为农夫乐事面团
     */
    @Override
    public Item asItem() {
        return FarmersUtil.getDough() == null ? super.asItem() : FarmersUtil.getDough();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(getFallCount());
    }

}