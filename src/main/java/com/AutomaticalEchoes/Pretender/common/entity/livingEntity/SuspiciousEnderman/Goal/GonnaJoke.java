package com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousEnderman.Goal;

import com.AutomaticalEchoes.Pretender.Pretender;
import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousEnderman.SuspiciousEnderman;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class GonnaJoke extends Goal {
    private final SuspiciousEnderman suspiciousEnderman;
    private final TargetingConditions jokeWithTargetConditions;
    private final Predicate<LivingEntity> LIVING_ENTITY_PREDICATE  = new Predicate<>() {
        @Override
        public boolean test(LivingEntity livingEntity) {
            if (suspiciousEnderman.getFirstPassenger() instanceof Cat) {
                return livingEntity instanceof Creeper;
            }
            if (suspiciousEnderman.getFirstPassenger() instanceof Wolf) {
                return livingEntity instanceof Skeleton;
            }
            if (!suspiciousEnderman.isCarryItemEmpty() || suspiciousEnderman.getFirstPassenger() instanceof Monster) {
                return livingEntity instanceof Player player && player.isInvulnerable();
            }
            return !(livingEntity instanceof Player player) || !player.isInvulnerable();
        }
    };

    public GonnaJoke(SuspiciousEnderman suspiciousEnderman) {
        this.setFlags(EnumSet.of(Flag.MOVE));
        this.suspiciousEnderman = suspiciousEnderman;
        this.jokeWithTargetConditions=TargetingConditions
                .forCombat()
                .range(this.suspiciousEnderman.getAttributeValue(Attributes.FOLLOW_RANGE))
                .selector(LIVING_ENTITY_PREDICATE);
    }

    @Override
    public boolean canUse() {
        return suspiciousEnderman.isClown() && !suspiciousEnderman.isJoking();
    }

    @Override
    public void start() {
        List<LivingEntity> entities = suspiciousEnderman.level.getNearbyEntities(LivingEntity.class,jokeWithTargetConditions,suspiciousEnderman,suspiciousEnderman.getBoundingBox().inflate(20.0));
        if(entities.size() >0 && suspiciousEnderman.isAngry()){
            entities.removeIf(entity -> !(entity instanceof Player));
        }
        int size = entities.size();
        if(size >0){
            int i = Pretender.RANDOM.nextInt(size);
            LivingEntity entity = entities.get(i);
            suspiciousEnderman.setJokingTarget(entity);
        }
    }
}
