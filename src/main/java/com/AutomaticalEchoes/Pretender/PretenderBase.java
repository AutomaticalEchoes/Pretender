package com.AutomaticalEchoes.Pretender;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(PretenderBase.MOD_ID)
public class PretenderBase {
    // Define mod id in a common place for everything to reference

    public static final String MOD_ID = "pretender";
    public static final CreativeModeTab PRETENDER_ITEM_TAB = new CreativeModeTab("Pretender") {
        @Override
        public ItemStack makeIcon() {
            return Items.CREEPER_BANNER_PATTERN.getDefaultInstance();
        }
    };
    public PretenderBase()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading

        // Register the Deferred Register to the mod event bus so blocks get registered
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent

    @SubscribeEvent
    public  void  registerCommands(RegisterCommandsEvent event){
    }
}
