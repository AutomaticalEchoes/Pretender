package com.AutomaticalEchoes.Pretender.common;

import com.AutomaticalEchoes.Pretender.Pretender;
import com.AutomaticalEchoes.Pretender.register.EffectsRegister;
import com.AutomaticalEchoes.Pretender.register.EntityRegister;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CommonEvents {
    @SubscribeEvent
    public static void LivingEntityDeath(LivingDeathEvent event){
       SummonSuspiciousCreeper(event);
    }

    public static void SummonSuspiciousCreeper(LivingDeathEvent event){
        if(event.getEntity().level instanceof ServerLevel serverLevel &&event.getEntity().getActiveEffectsMap().containsKey(EffectsRegister.RAGE_TARGET.get())){
           if(Pretender.RANDOM.nextInt(100) < 4) {
               EntityRegister.SUSPICIOUS_CREEPER.get().spawn(serverLevel,null,null,event.getEntity().blockPosition(), MobSpawnType.MOB_SUMMONED,false,false);
           }
        }
    }

}
