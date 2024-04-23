package cn.solarmoon.idyllic_food_diary.common.registry;

import cn.solarmoon.idyllic_food_diary.IdyllicFoodDiary;
import cn.solarmoon.solarmoon_core.api.registry.object.DamageTypeEntry;

/**
 * 伤害类型
 * 这个竟然能直接注册
 */
public class IMDamageTypes {

    //烫伤
    public static final DamageTypeEntry SCALD = IdyllicFoodDiary.REGISTRY.damageType()
            .id("scald")
            .build();

    //榴莲砸伤
    public static final DamageTypeEntry FALLING_DURIAN = IdyllicFoodDiary.REGISTRY.damageType()
            .id("falling_durian")
            .build();


}
