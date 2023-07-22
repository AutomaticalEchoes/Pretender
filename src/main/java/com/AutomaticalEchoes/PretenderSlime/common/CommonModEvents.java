package com.AutomaticalEchoes.PretenderSlime.common;

import com.AutomaticalEchoes.PretenderSlime.PretenderSlime;
import com.AutomaticalEchoes.PretenderSlime.api.PotionBrewingRecipe;
import com.AutomaticalEchoes.PretenderSlime.common.entity.livingEntity.SuspiciousSlime.SuspiciousSlime;
import com.AutomaticalEchoes.PretenderSlime.common.netWork.PacketHandler;
import com.AutomaticalEchoes.PretenderSlime.register.EntityRegister;
import com.AutomaticalEchoes.PretenderSlime.register.ItemsRegister;
import com.AutomaticalEchoes.PretenderSlime.register.PotionRegister;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.simple.SimpleChannel;

@Mod.EventBusSubscriber(modid = PretenderSlime.MOD_ID,bus=Mod.EventBusSubscriber.Bus.MOD)
public class CommonModEvents {
    public static SimpleChannel CHANNEL;
    @SubscribeEvent
    public static  void commonSetup(final FMLCommonSetupEvent event)
    {

        // Some common setup code
        event.enqueueWork(()->{
            CHANNEL = PacketHandler.RegisterPacket();
            SpawnPlacements.register(EntityRegister.SUSPICIOUS_SLIME.get(),SpawnPlacements.Type.ON_GROUND,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Mob::checkMobSpawnRules);

        });

        BrewingRecipeRegistry.addRecipe(new PotionBrewingRecipe(PotionRegister.ACIDITY.get(), Items.FERMENTED_SPIDER_EYE,PotionRegister.ACID_EROSION.get()));
        BrewingRecipeRegistry.addRecipe(new PotionBrewingRecipe(PotionRegister.ACIDITY.get(), ItemsRegister.SUSPICIOUS_SLIME_BALL.get(),PotionRegister.INVALID_ARMOR.get()));
    }

    @SubscribeEvent
    public static void registerEntityAttribute(EntityAttributeCreationEvent event){
        event.put(EntityRegister.SUSPICIOUS_SLIME.get(), SuspiciousSlime.createAttributes().build());
    }










}
