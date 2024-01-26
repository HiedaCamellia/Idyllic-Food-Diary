package cn.solarmoon.immersive_delight.api.common.entity_block;

import cn.solarmoon.immersive_delight.api.common.entity_block.entities.BaseTankContainerBlockEntity;
import cn.solarmoon.immersive_delight.client.IMSounds;
import cn.solarmoon.immersive_delight.api.common.entity_block.entities.BaseTankBlockEntity;
import cn.solarmoon.immersive_delight.compat.create.Create;
import cn.solarmoon.immersive_delight.network.serializer.ClientPackSerializer;
import cn.solarmoon.immersive_delight.util.ContainerHelper;
import cn.solarmoon.immersive_delight.api.util.FluidHelper;
import cn.solarmoon.immersive_delight.util.LevelSummonHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * 基本的具有流体和物品双重性的实体方块
 * 拥有基本的存取流体、物品功能
 * 能够保存记录各项数据，如方块掉落物、中键复制等
 * 可以只绑定容器或液体容器的其中一种方块实体
 * 默认拥有的特殊方法：
 * ① loadFluid 存取液体
 * ② storage 用手存取物品
 * ③ getThis 把方块直接拿到手中
 */
public abstract class BaseTCEntityBlock extends BasicEntityBlock {

    public BaseTCEntityBlock(Properties properties) {
        super(properties);
    }

    /**
     * 存取设置，0为能存能取，1为只能存不能取，2为只能取不能存，3为不能存取
     */
    public int fluidLoadSetting() {return 0;}

    /**
     * 倒水声设置，默认无
     * 如果为true，将禁用倒水的默认声改为本模组倒水声
     */
    public boolean pouringSound() {return false;}

    /**
     * 强制继承类实现use，因为容器一般需要特殊功能
     */
    @Override
    public abstract InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult);

    /**
     * 基本的存物逻辑，似乎可通用
     * 手不为空时存入，为空时疯狂取出
     */
    public boolean storage(BlockEntity blockEntity, Player player, InteractionHand hand) {
        if (blockEntity instanceof BaseTankContainerBlockEntity tc) {
            ItemStack heldItem = player.getItemInHand(hand);
            if (!heldItem.isEmpty()) {
                ItemStack result = tc.insertItem(heldItem);
                player.setItemInHand(hand, result);
                tc.setChanged();
                return !result.equals(heldItem);
            } else if (!tc.getStacks().isEmpty()) {
                ItemStack result = tc.extractItem();
                LevelSummonHelper.addItemToInventory(player, result);
                tc.setChanged();
                return !result.equals(heldItem);
            }
        }
        return false;
    }

    /**
     * 把forge的流体装取逻辑单独提出来方便万一的新物品对use的修改
     * 总之就是控制液体容器能自动装取液体
     */
    public boolean loadFluid(BaseTankBlockEntity tankEntity, Player player, Level level, BlockPos pos, InteractionHand hand) {
        if (fluidLoadSetting() == 3) return false;
        ItemStack heldItem = player.getItemInHand(hand);

        FluidActionResult result;
        FluidTank tank = tankEntity.tank;

        //存入液体
        //player填null：不发出声音
        if (fluidLoadSetting() == 0 || fluidLoadSetting() == 1) {
            Player flag = player;
            if (pouringSound()) flag = null;
            result = FluidUtil.tryEmptyContainer(heldItem, tank, Integer.MAX_VALUE, flag, true);
            if (result.isSuccess()) {
                if (!player.isCreative()) player.setItemInHand(hand, result.getResult());
                tankEntity.setChanged();
                if (pouringSound()) {
                    level.playSound(null, pos, IMSounds.PLAYER_POUR.get(), SoundSource.PLAYERS, 0.8F, 1F);
                } else Create.playPouringSound(tank.getFluid(), level, pos);
                return true;
            }
        }

        //取出液体
        if (fluidLoadSetting() == 0 || fluidLoadSetting() == 2) {
            result = FluidUtil.tryFillContainer(heldItem, tank, Integer.MAX_VALUE, player, true);
            if (result.isSuccess()) {
                if (!player.isCreative()) player.setItemInHand(hand, result.getResult());
                IFluidHandlerItem tankStack = FluidHelper.getTank(player.getItemInHand(hand));
                tankEntity.setChanged();
                Create.playFillingSound(tankStack.getFluidInTank(0), level, pos, player);
                return true;
            }
        }
        return false;
    }

    /**
     * 默认进行流体和容器的同步
     */
    @Override
    public void tick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        //防止放入液体时液体值和物品未在客户端同步 而 造成的 假右键操作
        if (blockEntity instanceof BaseTankContainerBlockEntity ct) {
            CompoundTag tag = new CompoundTag();
            tag.put("inventory", ct.inventory.serializeNBT());
            ClientPackSerializer.sendPacket(pos, ct.tank.getFluid(), tag, "updateTCBlock");
        }
    }

    /**
     * 使得放置物具有手上物品tank的读取
     * 以及item内的item的读取
     */
    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if(blockEntity == null) return;
        FluidHelper.setTank(blockEntity, stack);
        if(blockEntity instanceof BaseTankContainerBlockEntity ct) {
            ct.setInventory(stack);
        }
    }

    /**
     * 使得复制物具有该实体的tank内容
     * 以及内容物
     */
    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        ItemStack stack = super.getCloneItemStack(level, pos, state);
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if(blockEntity == null) return stack;
        FluidHelper.setTank(stack, blockEntity);
        if(blockEntity instanceof BaseTankContainerBlockEntity ct) {
            ContainerHelper.setInventory(stack, ct);
        }
        return stack;
    }

    /**
     * 让战利品表的同类掉落物存在tank信息
     * 和内容物信息
     */
    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        BlockEntity blockEntity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        ItemStack stack = new ItemStack(this);
        if(blockEntity != null) {
            FluidHelper.setTank(stack, blockEntity);
            if(blockEntity instanceof BaseTankContainerBlockEntity ct) {
                ContainerHelper.setInventory(stack, ct);
                return Collections.singletonList(stack);
            }
        }
        return super.getDrops(state, builder);
    }

    /**
     * 设定红石信号逻辑
     * 按液体或物品比例输出信号
     * 两者皆有的情况下优先判断流体
     */
    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof BaseTankContainerBlockEntity ct) {
            int itemSignal = ( (ct.getStacks().size() + 1) / ct.maxStackCount() ) * 15;
            int tankSignal = (int) (FluidHelper.getScale(ct.tank) * 15);
            return itemSignal == 15 ? itemSignal : tankSignal;
        }
        return 0;
    }

}