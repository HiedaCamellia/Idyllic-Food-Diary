package cn.solarmoon.idyllic_food_diary.element.matter.food;

import cn.solarmoon.solarmoon_core.api.renderer.BaseBlockEntityRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;

public class FoodBlockRenderer<E extends FoodBlockEntity> extends BaseBlockEntityRenderer<E> {

    public FoodBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(E fb, float v, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay) {
        BlockState container = fb.getContainerLeft();
        context.getBlockRenderDispatcher().renderSingleBlock(container, poseStack, buffer, light, overlay);
    }

}
