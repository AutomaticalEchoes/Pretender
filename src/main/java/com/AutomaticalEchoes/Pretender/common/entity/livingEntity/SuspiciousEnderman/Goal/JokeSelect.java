package com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousEnderman.Goal;

import com.AutomaticalEchoes.Pretender.Pretender;
import com.AutomaticalEchoes.Pretender.api.JokeCase;
import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousEnderman.SuspiciousEnderman;
import net.minecraft.world.entity.Entity;
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

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class JokeSelect extends Goal {
    private final SuspiciousEnderman suspiciousEnderman;
    private final TargetingConditions jokeWithTargetConditions;
    private JokeCase.Case Case = JokeCase.Case.EMPTY;
    private final Predicate<JokeCase> JOKE_PREDICATE  = joke -> {

    };
    private static final ArrayList<JokeCase> JOKE_LIST = new ArrayList<>();
    public JokeSelect(SuspiciousEnderman suspiciousEnderman) {
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

    }

    public void CheckCase(){
        if(suspiciousEnderman.CarryNothing()){
            Case = JokeCase.Case.EMPTY;
        }else if(!suspiciousEnderman.isCarryItemEmpty()){
            Case = JokeCase.Case.CARRIED;
        }else if(!suspiciousEnderman.getPassengers().isEmpty()){
            Case = JokeCase.Case.CATCHING;
        }
    }

    public void SelectTarget(){
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

    public JokeCase SelectJoke(){
        JokeCase.Case CASE = this.Case;
        List<JokeCase> jokeCases = JOKE_LIST.stream().filter(jokeCase -> jokeCase.canJoke() && jokeCase.Case() == CASE).toList();
        int size = e
    }
}
