package com.AutomaticalEchoes.Pretender.client.Renderer;

import com.AutomaticalEchoes.Pretender.Pretender;
import com.AutomaticalEchoes.Pretender.client.Layer.ItemLayer;
import com.AutomaticalEchoes.Pretender.client.Layer.SusSlimeOuterLayer;
import com.AutomaticalEchoes.Pretender.client.model.SusSlimeModel;
import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousSlime.SuspiciousSlime;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SuspiciousSlimeRender extends MobRenderer<SuspiciousSlime, SusSlimeModel<SuspiciousSlime>> {
    private static final ResourceLocation SLIME_LOCATION = new ResourceLocation(Pretender.MOD_ID ,"textures/model/entity/slime.png");

    public SuspiciousSlimeRender(EntityRendererProvider.Context p_174391_) {
        super(p_174391_, new SusSlimeModel<>(p_174391_.bakeLayer(SusSlimeModel.LAYER_LOCATION_SLIME)), 0.25F);
        this.addLayer(new SusSlimeOuterLayer<>(this, p_174391_.getModelSet()));
        this.addLayer(new ItemLayer<>(this));
    }

    public void render(SuspiciousSlime p_115976_, float p_115977_, float p_115978_, PoseStack p_115979_, MultiBufferSource p_115980_, int p_115981_) {
        this.shadowRadius = 0.25F * (float)p_115976_.getSize();
        super.render(p_115976_, p_115977_, p_115978_, p_115979_, p_115980_, p_115981_);
    }

    protected void scale(SuspiciousSlime p_115983_, PoseStack p_115984_, float p_115985_) {
        float f = 0.999F;
        p_115984_.scale(0.999F, 0.999F, 0.999F);
        p_115984_.translate(0.0D, (double)0.001F, 0.0D);
        float f1 = (float)p_115983_.getSize();
        float f2 = Mth.lerp(p_115985_, p_115983_.oSquish, p_115983_.squish) / (f1 * 0.5F + 1.0F);
        float f3 = 1.0F / (f2 + 1.0F);
        p_115984_.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
    }

    public ResourceLocation getTextureLocation(SuspiciousSlime p_115974_) {
        return SLIME_LOCATION;
    }
}
