package cn.solarmoon.immersive_delight.common.registry;


import cn.solarmoon.immersive_delight.ImmersiveDelight;
import cn.solarmoon.immersive_delight.common.block.DurianBlock;
import cn.solarmoon.immersive_delight.common.block.GarlicBlock;
import cn.solarmoon.immersive_delight.common.block.SteamerLidBlock;
import cn.solarmoon.immersive_delight.common.block.crop.*;
import cn.solarmoon.immersive_delight.common.block.entity_block.*;
import cn.solarmoon.immersive_delight.common.block.long_press_eat_block.*;
import cn.solarmoon.immersive_delight.common.block.sapling.AppleSaplingBlock;
import cn.solarmoon.immersive_delight.common.block.sapling.DurianSaplingBlock;
import cn.solarmoon.solarmoon_core.registry.core.IRegister;
import cn.solarmoon.solarmoon_core.registry.object.BlockEntry;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public enum IMBlocks implements IRegister {
    INSTANCE;

    //面团
    public static final BlockEntry<WheatDoughBlock> WHEAT_DOUGH = ImmersiveDelight.REGISTRY.block()
            .id("wheat_dough")
            .bound(WheatDoughBlock::new)
            .build();

    //面饼
    public static final BlockEntry<FlatbreadDoughBlock> FLATBREAD_DOUGH = ImmersiveDelight.REGISTRY.block()
            .id("flatbread_dough")
            .bound(FlatbreadDoughBlock::new)
            .build();

    //碗装蛋液
    public static final BlockEntry<BowlFoodUnShapeBlock> EGG_LIQUID_BOWL = ImmersiveDelight.REGISTRY.block()
            .id("egg_liquid_bowl")
            .bound(BowlFoodUnShapeBlock::new)
            .build();

    //鸡蛋羹
    public static final BlockEntry<BowlFoodUnShapeBlock> STEAMED_EGG_CUSTARD = ImmersiveDelight.REGISTRY.block()
            .id("steamed_egg_custard")
            .bound(BowlFoodUnShapeBlock::new)
            .build();

    //馒头
    public static final BlockEntry<SteamedBunBlock> STEAMED_BUN = ImmersiveDelight.REGISTRY.block()
            .id("steamed_bun")
            .bound(SteamedBunBlock::new)
            .build();

    //蘑菇煲
    public static final BlockEntry<BowlSoupBlock> MUSHROOM_STEW = ImmersiveDelight.REGISTRY.block()
            .id("mushroom_stew")
            .bound(() -> new BowlSoupBlock(){public Item asItem() {return Items.MUSHROOM_STEW;}})
            .build();

    //甜菜汤
    public static final BlockEntry<BowlSoupBlock> BEETROOT_SOUP = ImmersiveDelight.REGISTRY.block()
            .id("beetroot_soup")
            .bound(() -> new BowlSoupBlock(){public Item asItem() {return Items.BEETROOT_SOUP;}})
            .build();

    //南瓜汤
    public static final BlockEntry<BowlSoupBlock> PUMPKIN_SOUP = ImmersiveDelight.REGISTRY.block()
            .id("pumpkin_soup")
            .bound(BowlSoupBlock::new)
            .build();

    //藏书羊肉汤
    public static final BlockEntry<BowlSoupBlock> CANGSHU_MUTTON_SOUP = ImmersiveDelight.REGISTRY.block()
            .id("cangshu_mutton_soup")
            .bound(BowlSoupBlock::new)
            .build();

    //紫菜蛋花汤
    public static final BlockEntry<BowlSoupBlock> SEAWEED_EGG_DROP_SOUP = ImmersiveDelight.REGISTRY.block()
            .id("seaweed_egg_drop_soup")
            .bound(BowlSoupBlock::new)
            .build();

    //------------------------------------------------------------------------------------------//

    //苹果树苗
    public static final BlockEntry<AppleSaplingBlock> APPLE_SAPLING = ImmersiveDelight.REGISTRY.block()
            .id("apple_sapling")
            .bound(AppleSaplingBlock::new)
            .build();

    //苹果作物
    public static final BlockEntry<AppleCropBlock> APPLE_CROP = ImmersiveDelight.REGISTRY.block()
            .id("apple_crop")
            .bound(AppleCropBlock::new)
            .build();

    //绿茶
    public static final BlockEntry<GreenTeaPlantCropBlock> GREEN_TEA_PLANT = ImmersiveDelight.REGISTRY.block()
            .id("green_tea_plant")
            .bound(GreenTeaPlantCropBlock::new)
            .build();

    //红茶
    public static final BlockEntry<BlackTeaTreePlantBlock> BLACK_TEA_PLANT = ImmersiveDelight.REGISTRY.block()
            .id("black_tea_plant")
            .bound(BlackTeaTreePlantBlock::new)
            .build();

    //榴莲作物
    public static final BlockEntry<DurianCropBlock> DURIAN_CROP = ImmersiveDelight.REGISTRY.block()
            .id("durian_crop")
            .bound(DurianCropBlock::new)
            .build();

    //榴莲方块
    public static final BlockEntry<DurianBlock> DURIAN = ImmersiveDelight.REGISTRY.block()
            .id("durian")
            .bound(DurianBlock::new)
            .build();

    //榴莲树苗
    public static final BlockEntry<DurianSaplingBlock> DURIAN_SAPLING = ImmersiveDelight.REGISTRY.block()
            .id("durian_sapling")
            .bound(DurianSaplingBlock::new)
            .build();

    //大蒜作物
    public static final BlockEntry<GarlicBlock> GARLIC_CROP = ImmersiveDelight.REGISTRY.block()
            .id("garlic_crop")
            .bound(GarlicCropBlock::new)
            .build();

    //大蒜
    public static final BlockEntry<GarlicBlock> GARLIC = ImmersiveDelight.REGISTRY.block()
            .id("garlic")
            .bound(GarlicBlock::new)
            .build();

    //————————————————————————————————————————————————————————————————————————————————//

    //野生大蒜
    public static final BlockEntry<WildCropBlock> WILD_GARLIC = ImmersiveDelight.REGISTRY.block()
            .id("wild_garlic")
            .bound(() -> new WildCropBlock(MobEffects.UNLUCK, 8))
            .build();

    //————————————————————————————————————————————————————————————————————————————————//

    //青瓷杯
    public static final BlockEntry<CeladonCupEntityBlock> CELADON_CUP = ImmersiveDelight.REGISTRY.block()
            .id("celadon_cup")
            .bound(CeladonCupEntityBlock::new)
            .build();

    //玉瓷杯
    public static final BlockEntry<JadeChinaCupEntityBlock> JADE_CHINA_CUP = ImmersiveDelight.REGISTRY.block()
            .id("jade_china_cup")
            .bound(JadeChinaCupEntityBlock::new)
            .build();

    //水壶
    public static final BlockEntry<KettleEntityBlock> KETTLE = ImmersiveDelight.REGISTRY.block()
            .id("kettle")
            .bound(KettleEntityBlock::new)
            .build();

    //汤锅
    public static final BlockEntry<SoupPotEntityBlock> SOUP_POT = ImmersiveDelight.REGISTRY.block()
            .id("soup_pot")
            .bound(SoupPotEntityBlock::new)
            .build();

    //砧板
    public static final BlockEntry<CuttingBoardEntityBlock> CUTTING_BOARD = ImmersiveDelight.REGISTRY.block()
            .id("cutting_board")
            .bound(CuttingBoardEntityBlock::new)
            .build();

    //烧烤架
    public static final BlockEntry<CuttingBoardEntityBlock> GRILL = ImmersiveDelight.REGISTRY.block()
            .id("grill")
            .bound(GrillEntityBlock::new)
            .build();

    //盘子
    public static final BlockEntry<PlateEntityBlock> PLATE = ImmersiveDelight.REGISTRY.block()
            .id("plate")
            .bound(PlateEntityBlock::new)
            .build();

    //蒸笼
    public static final BlockEntry<SteamerEntityBlock> STEAMER = ImmersiveDelight.REGISTRY.block()
            .id("steamer")
            .bound(SteamerEntityBlock::new)
            .build();

    //蒸笼底座
    public static final BlockEntry<SteamerBaseEntityBlock> STEAMER_BASE = ImmersiveDelight.REGISTRY.block()
            .id("steamer_base")
            .bound(SteamerBaseEntityBlock::new)
            .build();

    //蒸笼盖
    public static final BlockEntry<SteamerLidBlock> STEAMER_LID = ImmersiveDelight.REGISTRY.block()
            .id("steamer_lid")
            .bound(SteamerLidBlock::new)
            .build();

}
