package com.AutomaticalEchoes.Pretender.client;


import com.AutomaticalEchoes.Pretender.Pretender;
import com.AutomaticalEchoes.Pretender.client.Renderer.SuspiciousEndermanRender;
import com.AutomaticalEchoes.Pretender.register.EntityRegister;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;


// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@Mod.EventBusSubscriber(modid = Pretender.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public  class ClientModEvents {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        event.enqueueWork(() -> {
        });
    }



    @SubscribeEvent
    public static void RegisterRenders(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(EntityRegister.SUSPICIOUS_ENDERMAN.get(), SuspiciousEndermanRender::new);
        event.registerEntityRenderer(EntityRegister.SUSPICIOUS_THROWN_ENDERPEARL_PROJECTILE.get(), p_174010_ -> new ThrownItemRenderer<>(p_174010_, 1.0F, true));
    }


    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
    }

    @SubscribeEvent
    public static void RegisterBlockColor(RegisterColorHandlersEvent.Block event){
    }


    @SubscribeEvent
    public static void RegisterItemColor(RegisterColorHandlersEvent.Item event){
    }



}
