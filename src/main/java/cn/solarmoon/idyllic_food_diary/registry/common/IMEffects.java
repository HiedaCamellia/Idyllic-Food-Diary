package cn.solarmoon.idyllic_food_diary.registry.common;


import cn.solarmoon.idyllic_food_diary.IdyllicFoodDiary;
import cn.solarmoon.idyllic_food_diary.element.effect.FleetOfFootEffect;
import cn.solarmoon.idyllic_food_diary.element.effect.RefreshmentEffect;
import cn.solarmoon.idyllic_food_diary.element.effect.SnugEffect;
import cn.solarmoon.idyllic_food_diary.element.effect.TeaAromaEffect;
import cn.solarmoon.solarmoon_core.api.entry.common.EffectEntry;

public class IMEffects {
    public static void register() {}

    public static final EffectEntry TEA_AROMA = IdyllicFoodDiary.REGISTRY.effect()
            .id("tea_aroma")
            .bound(TeaAromaEffect::new)
            .build();

    public static final EffectEntry REFRESHMENT = IdyllicFoodDiary.REGISTRY.effect()
            .id("refreshment")
            .bound(RefreshmentEffect::new)
            .build();

    public static final EffectEntry FLEET_OF_FOOT = IdyllicFoodDiary.REGISTRY.effect()
            .id("fleet_of_foot")
            .bound(FleetOfFootEffect::new)
            .build();

    public static final EffectEntry SNUG = IdyllicFoodDiary.REGISTRY.effect()
            .id("snug")
            .bound(SnugEffect::new)
            .build();

}
