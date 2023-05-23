package com.AutomaticalEchoes.Pretender.common;

import com.AutomaticalEchoes.Pretender.Pretender;
import com.AutomaticalEchoes.Pretender.api.PotionBrewingRecipe;
import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SusiciousSkeleton.SuspiciousSkeleton;
import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousCreeper.SuspiciousCreeper;
import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousEnderman.SuspiciousEnderman;
import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousSlime.SuspiciousSlime;
import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousSpider.SuspiciousSpider;
import com.AutomaticalEchoes.Pretender.common.netWork.PacketHandler;
import com.AutomaticalEchoes.Pretender.register.EntityRegister;
import com.AutomaticalEchoes.Pretender.register.ItemsRegister;
import com.AutomaticalEchoes.Pretender.register.PotionRegister;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.simple.SimpleChannel;

@Mod.EventBusSubscriber(modid = Pretender.MOD_ID,bus=Mod.EventBusSubscriber.Bus.MOD)
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
            SpawnPlacements.register(EntityRegister.SUSPICIOUS_ENDERMAN.get(),SpawnPlacements.Type.ON_GROUND,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Mob::checkMobSpawnRules);
            SpawnPlacements.register(EntityRegister.SUSPICIOUS_CREEPER.get(),SpawnPlacements.Type.ON_GROUND,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMonsterSpawnRules);
            SpawnPlacements.register(EntityRegister.SUSPICIOUS_SKELETON.get(),SpawnPlacements.Type.ON_GROUND,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMonsterSpawnRules);
            SpawnPlacements.register(EntityRegister.SUSPICIOUS_SPIDER.get(),SpawnPlacements.Type.ON_GROUND,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMonsterSpawnRules);

        });

        BrewingRecipeRegistry.addRecipe(new PotionBrewingRecipe(Potions.AWKWARD, ItemsRegister.SUSPICIOUS_CREEPER_SAC.get(), PotionRegister.RAGE_TARGET.get()));
        BrewingRecipeRegistry.addRecipe(new PotionBrewingRecipe(PotionRegister.ACIDITY.get(), Items.FERMENTED_SPIDER_EYE,PotionRegister.ACID_EROSION.get()));
        BrewingRecipeRegistry.addRecipe(new PotionBrewingRecipe(PotionRegister.SUS_WATER.get(),Items.NETHER_WART,PotionRegister.ENTANGLEMENT.get()));
        BrewingRecipeRegistry.addRecipe(new PotionBrewingRecipe(PotionRegister.ACIDITY.get(), ItemsRegister.SUSPICIOUS_SLIME_BALL.get(),PotionRegister.INVALID_ARMOR.get()));
    }

    @SubscribeEvent
    public static void registerEntityAttribute(EntityAttributeCreationEvent event){
        event.put(EntityRegister.SUSPICIOUS_CREEPER.get(), SuspiciousCreeper.createAttributes().build());
        event.put(EntityRegister.SUSPICIOUS_ENDERMAN.get(), SuspiciousEnderman.createAttributes().build());
        event.put(EntityRegister.SUSPICIOUS_SLIME.get(), SuspiciousSlime.createAttributes().build());
        event.put(EntityRegister.SUSPICIOUS_SKELETON.get(), SuspiciousSkeleton.createAttributes().build());
        event.put(EntityRegister.SUSPICIOUS_SPIDER.get(), SuspiciousSpider.createAttributes().build());
    }








}
