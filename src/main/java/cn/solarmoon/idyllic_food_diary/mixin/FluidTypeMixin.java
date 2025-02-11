package cn.solarmoon.idyllic_food_diary.mixin;

import cn.solarmoon.idyllic_food_diary.feature.fluid_temp.Temp;
import cn.solarmoon.idyllic_food_diary.feature.tea_brewing.TeaBrewingUtil;
import net.minecraft.network.chat.Component;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FluidType.class)
public abstract class FluidTypeMixin {

    @Shadow public abstract Component getDescription();

    @Inject(remap = false, method = "getDescription(Lnet/minecraftforge/fluids/FluidStack;)Lnet/minecraft/network/chat/Component;", at = @At("HEAD"), cancellable = true)
    public void getDisplayName(FluidStack fluidStack, CallbackInfoReturnable<Component> cir) {
        Component teaName = TeaBrewingUtil.getTeaInName(fluidStack);
        Component tempName = Temp.get(fluidStack).getPrefix();
        cir.setReturnValue(tempName.copy().append(teaName).append(getDescription()));
    }

}
