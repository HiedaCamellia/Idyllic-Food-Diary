package cn.solarmoon.immersive_delight.api.common.block.food;

import cn.solarmoon.immersive_delight.api.common.block.BaseWaterBlock;
import cn.solarmoon.immersive_delight.api.util.TextUtil;
import cn.solarmoon.immersive_delight.util.LevelSummonHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * 基本的拿取类型的食物
 * 可选拿取所需的物品
 * 根据拿取数改变形态
 */
public abstract class BaseTakenFoodBlock extends BaseWaterBlock {

    public static final IntegerProperty REMAIN = IntegerProperty.create("remain", 0, 10);

    public final int maxRemain;
    //必须用item而不是itemStack
    public final Item food;
    public final Item container;

    /**
     * @param maxRemain 食物可被获取次数（形态数）
     * @param food 右键所获得的食物
     * @param container 获得食物所需的容器（需要拿着右键才能获取食物的物品）
     *                  为空的话则任何手都能拿
     */
    public BaseTakenFoodBlock(Properties properties, int maxRemain, Item food, Item container) {
        super(properties);
        this.maxRemain = maxRemain;
        this.food = food;
        this.container = container;
        this.registerDefaultState(this.getStateDefinition().any().setValue(REMAIN, maxRemain).setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    /**
     * @param maxRemain 食物可被获取次数（形态数）
     * @param food 右键所获得的食物
     */
    public BaseTakenFoodBlock(Properties properties, int maxRemain, Item food) {
        super(properties);
        this.maxRemain = maxRemain;
        this.food = food;
        this.container = Items.AIR;
        this.registerDefaultState(this.getStateDefinition().any().setValue(REMAIN, maxRemain).setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    public IntegerProperty getRemain() {return REMAIN;}

    public ItemStack getFood() {return new ItemStack(food);}

    public ItemStack getContainer() {return new ItemStack(container);}

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack heldItem = player.getItemInHand(hand);
        int remain = state.getValue(getRemain());
        if (remain > 0) {
            if ((heldItem.is(this.container) || this.container.equals(Items.AIR))) {

                level.setBlock(pos, state.setValue(getRemain(), remain - 1), 3);

                heldItem.shrink(1);

                if (!this.container.equals(Items.AIR))
                    LevelSummonHelper.addItemToInventoryPerfectly(player, this.getFood(), heldItem, hand);
                else LevelSummonHelper.addItemToInventory(player, this.getFood());

                level.playSound(null, pos, SoundEvents.ARMOR_EQUIP_LEATHER, SoundSource.PLAYERS);

                return InteractionResult.SUCCESS;
            } else player.displayClientMessage(TextUtil.translation("message", "container_required", this.getContainer().getHoverName()), true);
        }
        return InteractionResult.PASS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(REMAIN);
    }

    /**
     * 只能生存在有足够碰撞箱的方块上
     */
    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).isSolid();
    }

    /**
     * 红石信号规则：根据剩余可拿取食物比例数减小
     * 也就是放出来满格，越拿越少
     */
    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return (int) ((state.getValue(getRemain()) / (float) this.maxRemain) * 15);
    }

    @Override
    public abstract VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context);

}
