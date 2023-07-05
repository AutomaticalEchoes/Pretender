package com.AutomaticalEchoes.Pretender.common;

import com.AutomaticalEchoes.Pretender.Pretender;
import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousEnderman.SuspiciousEnderman;
import com.AutomaticalEchoes.Pretender.common.netWork.PacketHandler;
import com.AutomaticalEchoes.Pretender.register.EntityRegister;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
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
            SpawnPlacements.register(EntityRegister.SUSPICIOUS_ENDERMAN.get(),SpawnPlacements.Type.ON_GROUND,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Mob::checkMobSpawnRules);

        });

    }

    @SubscribeEvent
    public static void registerEntityAttribute(EntityAttributeCreationEvent event){
        event.put(EntityRegister.SUSPICIOUS_ENDERMAN.get(), SuspiciousEnderman.createAttributes().build());
    }








}
