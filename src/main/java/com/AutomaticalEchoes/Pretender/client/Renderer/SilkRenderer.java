package com.AutomaticalEchoes.Pretender.client.Renderer;

import com.AutomaticalEchoes.Pretender.Pretender;
import com.AutomaticalEchoes.Pretender.client.Model.SilkModel;
import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousSpider.Silk;
import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousSpider.SuspiciousSpider;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;

public class SilkRenderer extends EntityRenderer<Silk> {
    public static final ResourceLocation RESOURCE_LOCATION=new ResourceLocation(Pretender.MOD_RESOURCES+"textures/model/entity/silk/texture.png");
    public static final RenderType Silk_MAIN= RenderType.leash();
    private final SilkModel<Silk> silkModel;
    public SilkRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
        this.silkModel = new SilkModel<>(p_174008_.bakeLayer(SilkModel.LAYER_LOCATION));
    }

    @Override
    public ResourceLocation getTextureLocation(Silk p_114482_) {
        return RESOURCE_LOCATION;
    }


    @Override
    public boolean shouldRender(Silk p_114491_, Frustum p_114492_, double p_114493_, double p_114494_, double p_114495_) {
        return true;
    }

    @Override
    public void render(Silk p_114485_, float p_114486_, float p_114487_, PoseStack p_114488_, MultiBufferSource p_114489_, int p_114490_) {
        super.render(p_114485_, p_114486_, p_114487_, p_114488_, p_114489_, p_114490_);
        p_114488_.pushPose();
        p_114488_.mulPose(Vector3f.YP.rotationDegrees(180.0F - p_114486_));
        VertexConsumer vertexConsumer = p_114489_.getBuffer(silkModel.renderType(getTextureLocation(p_114485_)));
        silkModel.renderToBuffer(p_114488_,vertexConsumer,p_114490_, OverlayTexture.NO_OVERLAY,1.0F,1.0F,1.0F,1.0F);
        p_114488_.popPose();
        if(p_114485_.getOwner()!=null&&p_114485_.getOwner() instanceof SuspiciousSpider suspiciousSpider)
        renderChain(p_114485_,p_114487_,p_114488_,p_114489_,suspiciousSpider);
    }


    private <E extends Entity> void renderChain(Silk p_115462_, float p_115463_, PoseStack p_115464_, MultiBufferSource p_115465_, E p_115466_) {
        if(p_115462_.distanceTo(p_115466_) <= 1.5F) return;
        p_115464_.pushPose();
        Vec3 vec3 =  p_115466_.getEyePosition().add(0,-0.25,0);
        double d0 = (Mth.lerp(p_115463_, p_115462_.yo, p_115462_.getY()) * ((float)Math.PI / 180F)) + (Math.PI / 2D);
        Vec3 vec31 =new Vec3(0,-0.25D,0);
        double d1 = Math.cos(d0) * vec31.z + Math.sin(d0) * vec31.x;
        double d2 = Math.sin(d0) * vec31.z - Math.cos(d0) * vec31.x;
        double d3 = Mth.lerp(p_115463_, p_115462_.xo, p_115462_.getX()) + d1;
        double d4 = Mth.lerp(p_115463_, p_115462_.yo, p_115462_.getY()) + vec31.y;
        double d5 = Mth.lerp(p_115463_, p_115462_.zo, p_115462_.getZ()) + d2;
        p_115464_.translate(d1, vec31.y, d2);
        float f = (float)(vec3.x - d3);
        float f1 = (float)(vec3.y - d4);
        float f2 = (float)(vec3.z - d5);
        float f3 = 0.025F;
        VertexConsumer vertexconsumer = p_115465_.getBuffer(Silk_MAIN);
        Matrix4f matrix4f = p_115464_.last().pose();
        float f4 = Mth.fastInvSqrt(f * f + f2 * f2) * 0.025F / 2.0F;
        float f5 = f2 * f4;
        float f6 = f * f4;
        BlockPos blockpos = new BlockPos(p_115462_.getEyePosition(p_115463_));
        BlockPos blockpos1 = new BlockPos(p_115466_.getEyePosition(p_115463_));
        int i=7;
        int j=i;
        int k = p_115462_.level.getBrightness(LightLayer.SKY, blockpos);
        int l = p_115462_.level.getBrightness(LightLayer.SKY, blockpos1);

        for(int i1 = 0; i1 <= 24; ++i1) {
            addVertexPair(vertexconsumer, matrix4f, f, f1, f2, i, j, k, l, 0.025F, 0.025F, f5, f6, i1, false);
        }

        for(int j1 = 24; j1 >= 0; --j1) {
            addVertexPair(vertexconsumer, matrix4f, f, f1, f2, i, j, k, l, 0.025F, 0.0F, f5, f6, j1, true);
        }

        p_115464_.popPose();
    }


    private static void addVertexPair(VertexConsumer p_174308_, Matrix4f p_174309_, float p_174310_, float p_174311_, float p_174312_, int p_174313_, int p_174314_, int p_174315_, int p_174316_, float p_174317_, float p_174318_, float p_174319_, float p_174320_, int p_174321_, boolean p_174322_) {
        float f = (float)p_174321_ / 24.0F;
        int i = (int)Mth.lerp(f, (float)p_174313_, (float)p_174314_);
        int j = (int)Mth.lerp(f, (float)p_174315_, (float)p_174316_);
        int k = LightTexture.pack(i, j);
        float f5 = p_174310_ * f;
        float f6 = p_174311_ > 0.0F ? p_174311_ * f * f : p_174311_ - p_174311_ * (1.0F - f) * (1.0F - f);
        float f7 = p_174312_ * f;
        p_174308_.vertex(p_174309_, f5 - p_174319_, f6 + p_174318_, f7 + p_174320_).color(1.0F, 1.0F, 1.0F, 0.5F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(k).endVertex();
        p_174308_.vertex(p_174309_, f5 + p_174319_, f6 + p_174317_ - p_174318_, f7 - p_174320_).color(1.0F, 1.0F, 1.0F, 0.5F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(k).endVertex();
    }
}
