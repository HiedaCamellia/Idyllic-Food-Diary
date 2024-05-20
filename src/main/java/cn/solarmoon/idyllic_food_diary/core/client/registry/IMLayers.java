package cn.solarmoon.idyllic_food_diary.core.client.registry;

import cn.solarmoon.idyllic_food_diary.IdyllicFoodDiary;
import cn.solarmoon.idyllic_food_diary.core.client.model.humanoid.ConjurerHatModel;
import cn.solarmoon.solarmoon_core.api.client.registry.LayerEntry;

public class IMLayers {
    public static void register() {}

    public static final LayerEntry BATHROBE = IdyllicFoodDiary.REGISTRY.layer()
            .id("bathrobe")
            .bound(ConjurerHatModel::createLayer)
            .build();

}