package com.AutomaticalEchoes.PretenderSlime.client;


import com.AutomaticalEchoes.PretenderSlime.PretenderSlime;
import com.AutomaticalEchoes.PretenderSlime.api.Utils;
import com.AutomaticalEchoes.PretenderSlime.client.Renderer.SuspiciousSlimeAcidityRender;
import com.AutomaticalEchoes.PretenderSlime.client.Renderer.SuspiciousSlimeRender;
import com.AutomaticalEchoes.PretenderSlime.client.model.SusSlimeModel;
import com.AutomaticalEchoes.PretenderSlime.register.BlockRegister;
import com.AutomaticalEchoes.PretenderSlime.register.EntityRegister;
import com.AutomaticalEchoes.PretenderSlime.register.FluidRegister;
import com.AutomaticalEchoes.PretenderSlime.register.PotionRegister;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;


// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@Mod.EventBusSubscriber(modid = PretenderSlime.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public  class ClientModEvents {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        ItemBlockRenderTypes.setRenderLayer(FluidRegister.MUCUS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(FluidRegister.ACIDITY.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(FluidRegister.ACIDITY_FLOW.get(), RenderType.translucent());
        event.enqueueWork(() -> {
        });
    }



    @SubscribeEvent
    public static void RegisterRenders(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(EntityRegister.ACIDITY.get(), SuspiciousSlimeAcidityRender::new);
        event.registerEntityRenderer(EntityRegister.SUSPICIOUS_SLIME.get(), SuspiciousSlimeRender::new);
    }


    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(SusSlimeModel.LAYER_LOCATION_SLIME,SusSlimeModel::createInnerBodyLayer);
        event.registerLayerDefinition(SusSlimeModel.LAYER_LOCATION_SLIME_OUTER,SusSlimeModel::createOuterBodyLayer);
    }

    @SubscribeEvent
    public static void RegisterBlockColor(RegisterColorHandlersEvent.Block event){
        event.register((p_92621_, p_92622_, p_92623_, p_92624_) -> p_92622_ != null && p_92623_ != null ? 0xA1953472 : -1, BlockRegister.ACIDITY_CAULDRON_BLOCK.get());
        event.register((p_92621_, p_92622_, p_92623_, p_92624_) -> p_92622_ != null && p_92623_ != null ? 0xA1639C58 : -1, BlockRegister.MUCUS_CAULDRON_BLOCK.get());
        event.register((p_92621_, p_92622_, p_92623_, p_92624_) -> p_92622_ != null && p_92623_ != null ? BiomeColors.getAverageWaterColor(p_92622_, p_92623_) : -1, BlockRegister.SUS_WATER_CAULDRON_BLOCK.get());
    }


    @SubscribeEvent
    public static void RegisterItemColor(RegisterColorHandlersEvent.Item event){
        event.register((p_92672_, p_92673_) -> {
            Potion potion = PotionUtils.getPotion(p_92672_);
            if(potion == PotionRegister.ACIDITY.get())
                return Utils.IColor(0xA1953472);
            if(potion == PotionRegister.MUCUS.get())
                return Utils.IColor(0xA1639C58);
            return p_92673_ > 0 ? -1 : PotionUtils.getColor(p_92672_);
        }, Items.POTION, Items.SPLASH_POTION, Items.LINGERING_POTION);
    }



}
