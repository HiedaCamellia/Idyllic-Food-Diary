package cn.solarmoon.idyllic_food_diary.registry.client;

import cn.solarmoon.idyllic_food_diary.IdyllicFoodDiary;
import cn.solarmoon.idyllic_food_diary.element.matter.bathrobe.ConjurerHatModel;
import cn.solarmoon.solarmoon_core.api.entry.client.LayerEntry;

public class IMLayers {
    public static void register() {}

    public static final LayerEntry BATHROBE = IdyllicFoodDiary.REGISTRY.layer()
            .id("bathrobe")
            .bound(ConjurerHatModel::createLayer)
            .build();

}
