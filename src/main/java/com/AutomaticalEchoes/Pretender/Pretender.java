package com.AutomaticalEchoes.Pretender;

import com.AutomaticalEchoes.Pretender.api.ICauldronInteraction;
import com.AutomaticalEchoes.Pretender.config.ModCommonConfig;
import com.AutomaticalEchoes.Pretender.register.*;
import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.Random;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(Pretender.MOD_ID)
public class Pretender
{
    // Define mod id in a common place for everything to reference

    public static final String MOD_ID = "pretender";
    public static final String MOD_RESOURCES = "pretender:";
    public static final Random RANDOM = new Random();
    public static final CreativeModeTab MY_TAB = new CreativeModeTab("Pretender") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.CREEPER_BANNER_PATTERN);
        }
    };
    // Directly reference a slf4j logger
   public static final Logger LOGGER = LogUtils.getLogger();

    public Pretender()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading

        // Register the Deferred Register to the mod event bus so blocks get registered
        PoiTypeRegister.DEFERRED_REGISTER.register(modEventBus);
        FluidRegister.DEFERRED_REGISTER.register(modEventBus);
        FluidRegister.Type.TYPE_DEFERRED_REGISTER.register(modEventBus);
        EffectsRegister.REGISTRY.register(modEventBus);
        EntityRegister.REGISTER.register(modEventBus);
        BlockRegister.DEFERRED_REGISTER.register(modEventBus);
        BlockRegister.BlockEntityRegister.DEFERRED_REGISTER.register(modEventBus);
        ItemsRegister.REGISTRY.register(modEventBus);
        PotionRegister.REGISTRY.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ModCommonConfig.SPEC,"myMod-common.toml");
    }


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        ICauldronInteraction.Init();
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent

}
