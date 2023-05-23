package com.AutomaticalEchoes.Pretender.common.effect;

import com.AutomaticalEchoes.Pretender.config.ModCommonConfig;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;


public class RageTargetEffect extends MobEffect {
    private static final Predicate<LivingEntity> LIVING_ENTITY_SELECTOR = (p_31504_) -> {
        return p_31504_.getMobType() != MobType.UNDEFINED&& p_31504_.attackable();
    };
    public static final  TargetingConditions targetingConditions=TargetingConditions
            .forCombat()
            .range(128.0D)
            .ignoreLineOfSight()
            .selector(LIVING_ENTITY_SELECTOR);
    public static final Random random=new Random();

    public RageTargetEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
    }

    @Override
    public void applyEffectTick(LivingEntity p_19467_, int p_19468_) {
        if(p_19467_.isAlive()){
            List<Monster> entities=p_19467_.level.getNearbyEntities(Monster.class,targetingConditions,p_19467_,p_19467_.getBoundingBox().inflate(128.0D));
            for (Monster monster:entities) {
                monster.setTarget(p_19467_);
                monster.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,2400,random.nextInt(2)+ModCommonConfig.RAGE_TARGET_EFFECT_SPEEDUP_LEVEL.get() ));
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int p_19455_, int p_19456_) {
       return p_19455_ % ( ModCommonConfig.RAGE_TARGET_EFFECT_DURATION_TICK.get()*20) ==0;
    }
}
