package cn.solarmoon.idyllic_food_diary.registry.common;

import cn.solarmoon.idyllic_food_diary.IdyllicFoodDiary;
import cn.solarmoon.idyllic_food_diary.compat.patchouli.PItems;
import cn.solarmoon.idyllic_food_diary.compat.patchouli.Patchouli;
import cn.solarmoon.solarmoon_core.api.entry.common.CreativeTabEntry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.RegistryObject;

import java.util.stream.Collectors;

public class IMCreativeModeTab {
    public static void register() {}

    public static final CreativeTabEntry CREATIVE_TAB = IdyllicFoodDiary.REGISTRY.creativeTab()
            .id(IdyllicFoodDiary.MOD_ID)
            .builder(CreativeModeTab.builder()
                            .title(IdyllicFoodDiary.TRANSLATOR.set("creative_mode_tab", "main"))
                            .icon(() -> new ItemStack(IMItems.ROLLING_PIN.get()))
                            .displayItems((params, output) -> {
                                        var list = IdyllicFoodDiary.REGISTRY.itemRegister.getEntries().stream()
                                                .map(RegistryObject::get)
                                                .collect(Collectors.toList());
                                        list.add(27, Items.APPLE);
                                        list.add(80, Items.WATER_BUCKET);
                                        list.add(82, Items.MILK_BUCKET);
                                        list.remove(list.size() - 1);
                                        if (Patchouli.isLoaded()) {
                                            list.add(0, PItems.FARMERS_DIARY.get());
                                        }
                                        list.add(58, Items.MUSHROOM_STEW);
                                        list.add(59, Items.BEETROOT_SOUP);
                                        list.add(13, Items.BOWL);
                                        list.forEach(output::accept);
                                    }
                                )
                    )
            .build();

}