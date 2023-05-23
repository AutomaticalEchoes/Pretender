package com.AutomaticalEchoes.Pretender.register;

import com.AutomaticalEchoes.Pretender.Pretender;
import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SusiciousSkeleton.SuspiciousSkeleton;
import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousCreeper.SuspiciousCreeper;
import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousEnderman.SuspiciousEnderman;
import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousSlime.SuspiciousSlime;
import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousSpider.Silk;
import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousSpider.SuspiciousSpider;
import com.AutomaticalEchoes.Pretender.common.entity.projectile.AcidityBall;
import com.AutomaticalEchoes.Pretender.common.entity.projectile.SuspiciousThrownEnderpearl;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class EntityRegister {
    public static final DeferredRegister<EntityType<?>> REGISTER= DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Pretender.MOD_ID);
    public static final RegistryObject<EntityType<SuspiciousCreeper>> SUSPICIOUS_CREEPER=REGISTER.register("suspicious_creeper",
            ()-> EntityType.Builder.of(SuspiciousCreeper::new,MobCategory.MONSTER).sized(1.0F,2.0F)
                    .clientTrackingRange(20)
                    .build("suspicious_creeper"));
    public static final RegistryObject<EntityType<SuspiciousEnderman>> SUSPICIOUS_ENDERMAN=REGISTER.register("suspicious_enderman",
            ()-> EntityType.Builder.of(SuspiciousEnderman::new,MobCategory.MONSTER).sized(1.0F,3.0F)
                    .clientTrackingRange(20)
                    .build("suspicious_endermann"));
    public static final RegistryObject<EntityType<AcidityBall>> ACIDITY=REGISTER.register("acidity",
            () -> EntityType.Builder.of(AcidityBall::Create,MobCategory.MISC).sized(0.5F,0.5F)
                    .build("acidity"));
    public static final RegistryObject<EntityType<SuspiciousSlime>> SUSPICIOUS_SLIME=REGISTER.register("suspicious_slime",
            () -> EntityType.Builder.of(SuspiciousSlime::new,MobCategory.MONSTER).sized(2.04F,2.04F)
                    .clientTrackingRange(20)
                    .build("suspicious_slime"));
    public static final RegistryObject<EntityType<SuspiciousSkeleton>> SUSPICIOUS_SKELETON=REGISTER.register("suspicious_skeleton",
            () -> EntityType.Builder.of(SuspiciousSkeleton::new,MobCategory.MONSTER).sized(1.0F,2.0F)
                    .clientTrackingRange(20)
                    .build("suspicious_skeleton"));
    public static final RegistryObject<EntityType<SuspiciousSpider>> SUSPICIOUS_SPIDER =REGISTER.register("suspicious_spider",
            () -> EntityType.Builder.of(SuspiciousSpider::new,MobCategory.MONSTER).sized(2.0F,1.0F)
                    .clientTrackingRange(20)
                    .build("suspicious_spider"));
    public static final RegistryObject<EntityType<Silk>> SILK = REGISTER.register("silk",
            () -> EntityType.Builder.of(Silk::Create,MobCategory.MISC).sized(1.0F,1.0F)
                    .build("silk"));

    public static final RegistryObject<EntityType<SuspiciousThrownEnderpearl>> SUSPICIOUS_THROWN_ENDERPEARL_PROJECTILE = REGISTER.register("suspicious_thrown_enderpearl",
            () -> EntityType.Builder.of(SuspiciousThrownEnderpearl::Create,MobCategory.MISC).sized(0.5f,0.5f)
                    .build("suspicious_thrown_enderpearl"));

}
