package com.AutomaticalEchoes.Pretender.register;

import com.AutomaticalEchoes.Pretender.Pretender;
import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousSlime.SuspiciousSlime;
import com.AutomaticalEchoes.Pretender.common.entity.projectile.AcidityBall;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class EntityRegister {
    public static final DeferredRegister<EntityType<?>> REGISTER= DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Pretender.MOD_ID);
    public static final RegistryObject<EntityType<AcidityBall>> ACIDITY=REGISTER.register("acidity",
            () -> EntityType.Builder.of(AcidityBall::Create,MobCategory.MISC).sized(0.5F,0.5F)
                    .build("acidity"));
    public static final RegistryObject<EntityType<SuspiciousSlime>> SUSPICIOUS_SLIME=REGISTER.register("suspicious_slime",
            () -> EntityType.Builder.of(SuspiciousSlime::new,MobCategory.MONSTER).sized(2.04F,2.04F)
                    .clientTrackingRange(20)
                    .build("suspicious_slime"));
}
