package com.AutomaticalEchoes.Pretender.client.Layer;

import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousSlime.SuspiciousSlime;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ItemLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private final ItemRenderer itemRenderer;
    public ItemLayer(RenderLayerParent<T, M> p_117346_) {
        super(p_117346_);
        this.itemRenderer = Minecraft.getInstance().getItemRenderer();
    }

    public void render(PoseStack p_117204_, MultiBufferSource p_117205_, int p_117206_, T p_117207_, float p_117208_, float p_117209_, float p_117210_, float p_117211_, float p_117212_, float p_117213_) {
        if (p_117207_ instanceof SuspiciousSlime suspiciousSlime && !suspiciousSlime.isCarryItemEmpty()) {
            p_117204_.pushPose();
            float scale = suspiciousSlime.getSize() == 1? 0.5F : 1;
            p_117204_.scale(scale,scale,scale);
            p_117204_.translate(0,1,0);
            ItemStack carriedItem = suspiciousSlime.getCarriedItem();
            BakedModel bakedmodel = this.itemRenderer.getModel(carriedItem, p_117207_.level, null, p_117207_.getId());
            this.itemRenderer.render(carriedItem, ItemTransforms.TransformType.GROUND, false, p_117204_, p_117205_, p_117206_, OverlayTexture.NO_OVERLAY, bakedmodel);
            p_117204_.popPose();
        }
    }

}
