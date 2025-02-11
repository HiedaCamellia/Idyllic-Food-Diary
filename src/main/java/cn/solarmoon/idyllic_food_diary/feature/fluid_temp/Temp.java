package cn.solarmoon.idyllic_food_diary.feature.fluid_temp;

import cn.solarmoon.idyllic_food_diary.IdyllicFoodDiary;
import com.google.gson.JsonObject;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public enum Temp {
    HOT, COMMON, COLD,
    ANY;

    public static final String TEMP = "Temp";

    /**
     * @return 除了比较温度等级本身以外，如果任意一个为ANY，就可以接受任意温度等级通过
     */
    public boolean isSame(Temp temp) {
        return this == temp || temp == Temp.ANY || this == Temp.ANY;
    }

    public Component getPrefix() {
        if (this == COMMON) return Component.empty();
        return IdyllicFoodDiary.TRANSLATOR.set("fluid", "temp." + this.toString().toLowerCase());
    }

    /**
     * 把新温度推入液体，注意，common类型的温度并非设置进去，而是没有温度tag时默认为common，不要强行将common推入Tag！！！
     * @return 设置好后的液体
     */
    public static FluidStack set(FluidStack fluidStack, Temp temp) {
        if (!fluidStack.isEmpty()) {
            CompoundTag tag = fluidStack.getTag();
            if (temp == COMMON && tag != null) tag.remove(TEMP);
            else if (temp != COMMON) fluidStack.getOrCreateTag().putString(TEMP, temp.toString());
        }
        return fluidStack;
    }

    /**
     * 获取温度，没有则返回常温
     */
    public static Temp get(FluidStack fluidStack) {
        if (!fluidStack.isEmpty()) {
            return get(fluidStack.getTag());
        }
        return COMMON;
    }

    public static Temp get(@Nullable CompoundTag tag) {
        return tag != null && tag.contains(TEMP) ? Temp.valueOf(tag.getString(TEMP)) : COMMON;
    }

    public static boolean shrink(FluidStack fluidStack) {
        if (fluidStack.isEmpty()) return false;
        Temp temp = get(fluidStack);
        switch (temp) {
            case HOT -> {
                set(fluidStack, COMMON);
                return true;
            }
            case COMMON -> {
                set(fluidStack, COLD);
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    public static boolean grow(FluidStack fluidStack) {
        if (fluidStack.isEmpty()) return false;
        Temp temp = get(fluidStack);
        switch (temp) {
            case COLD -> {
                set(fluidStack, COMMON);
                return true;
            }
            case COMMON -> {
                set(fluidStack, HOT);
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    public static boolean tick(FluidStack fluidStack) {
        Random random = new Random();
        switch (get(fluidStack)) {
            case COLD -> {
                if (random.nextInt(50000) == 0) {
                    IdyllicFoodDiary.DEBUG.send("已变温");
                    grow(fluidStack);
                    return true;
                }
            }
            case HOT -> {
                if (random.nextInt(50000) == 0) {
                    IdyllicFoodDiary.DEBUG.send("已变温");
                    shrink(fluidStack);
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isSame(FluidStack fluidStack, Temp temp) {
        return get(fluidStack).isSame(temp);
    }

    public static boolean isHot(FluidStack fluidStack) {
        return isSame(fluidStack, HOT);
    }

    public static boolean isCold(FluidStack fluidStack) {
        return isSame(fluidStack, COLD);
    }

    public static boolean isCommon(FluidStack fluidStack) {
        return isSame(fluidStack, COMMON);
    }

    public static Temp readFromJson(JsonObject json) {
        return Temp.valueOf(GsonHelper.getAsString(json, "temp", "any").toUpperCase());
    }

}
