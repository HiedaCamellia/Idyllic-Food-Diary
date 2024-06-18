package cn.solarmoon.idyllic_food_diary.mixin;

import cn.solarmoon.idyllic_food_diary.feature.basic_feature.FarmerUtil;
import cn.solarmoon.idyllic_food_diary.feature.tea_brewing.Temp;
import cn.solarmoon.idyllic_food_diary.network.NETList;
import cn.solarmoon.idyllic_food_diary.registry.common.IMPacks;
import cn.solarmoon.solarmoon_core.api.util.FluidUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public abstract class BlockMixin {

    @Inject(method = "animateTick", at = @At("HEAD"))
    public void tick(BlockState p_220827_, Level level, BlockPos pos, RandomSource p_220830_, CallbackInfo ci) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be != null) {
            IFluidHandler tank = FluidUtil.getTank(be);
            if (tank != null) {
                IMPacks.SERVER_PACK.getSender().pos(pos).fluidStack(tank.getFluidInTank(0)).send(NETList.TEMP);
            }
        }
    }

}