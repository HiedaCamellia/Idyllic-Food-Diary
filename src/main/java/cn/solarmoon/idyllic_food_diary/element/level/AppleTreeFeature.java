package cn.solarmoon.idyllic_food_diary.element.level;

import cn.solarmoon.idyllic_food_diary.registry.common.IMBlocks;
import cn.solarmoon.idyllic_food_diary.registry.common.IMFeatures;
import cn.solarmoon.solarmoon_core.api.level_feature.BaseTreeFeature;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import javax.annotation.Nullable;

public class AppleTreeFeature extends BaseTreeFeature {

    public AppleTreeFeature() {
    }

    /**
     * 类橡树的生成
     */
    @Override
    public void addSets(FeaturePlaceContext<BaseTreeFeature.Config> context) {
        BaseTreeFeature.Config config = context.config();
        RandomSource random = context.random();
        BlockPos pos = context.origin();

        //使得树高度具有随机性
        int trunkHeight = config.trunkPlacer.getTreeHeight(random);
        setBaseTrunk(pos, trunkHeight);

        //top：从顶部开始
        BlockPos top = pos.above(trunkHeight - 1);
        setBaseFoliage(top, 1, random);

        //加入果实坐标
        addProducts(random, 0.5f);
    }

    @Override
    public BlockState getSapling() {
        return IMBlocks.APPLE_SAPLING.get().defaultBlockState();
    }

    @Override
    public BlockState getProduct() {
        return IMBlocks.APPLE_CROP.get().defaultBlockState();
    }

    public static class AppleTreeGrower extends AbstractTreeGrower {
        @Override
        @Nullable
        protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource randomIn, boolean beehiveIn) {
            return IMFeatures.APPLE_TREE.getConfigKey();
        }
    }

}
