package com.AutomaticalEchoes.Pretender.register;

import com.AutomaticalEchoes.Pretender.Pretender;
import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousEnderman.SuspiciousEnderman;
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
    public static final RegistryObject<EntityType<SuspiciousEnderman>> SUSPICIOUS_ENDERMAN=REGISTER.register("suspicious_enderman",
            ()-> EntityType.Builder.of(SuspiciousEnderman::new,MobCategory.MONSTER).sized(1.0F,3.0F)
                    .clientTrackingRange(20)
                    .build("suspicious_endermann"));
    public static final RegistryObject<EntityType<SuspiciousThrownEnderpearl>> SUSPICIOUS_THROWN_ENDERPEARL_PROJECTILE = REGISTER.register("suspicious_thrown_enderpearl",
            () -> EntityType.Builder.of(SuspiciousThrownEnderpearl::Create,MobCategory.MISC).sized(0.5f,0.5f)
                    .build("suspicious_thrown_enderpearl"));

}
