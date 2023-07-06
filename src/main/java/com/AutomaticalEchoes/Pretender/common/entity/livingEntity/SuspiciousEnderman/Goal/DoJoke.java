package com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousEnderman.Goal;

import com.AutomaticalEchoes.Pretender.Pretender;
import com.AutomaticalEchoes.Pretender.api.JokeCase;
import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousEnderman.SuspiciousEnderman;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
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
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

public class DoJoke extends Goal {
    private final SuspiciousEnderman suspiciousEnderman;
    private JokeCase<? extends LivingEntity> joke;
    private int tick = 0 ;

    public DoJoke(SuspiciousEnderman suspiciousEnderman) {
        this.suspiciousEnderman = suspiciousEnderman;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return suspiciousEnderman.isJoking() && !suspiciousEnderman.isScared();
    }

    @Override
    public void start() {
        this.joke = suspiciousEnderman.getJoke();
        this.tick = 0;
    }

    @Override
    public void tick() {
        this.tick++;
        if(tick == 10) {
            this.suspiciousEnderman.teleportAim(suspiciousEnderman.getJokingTarget().position(),2);
        }else if(tick == 20){
            LivingEntity jokingTarget = suspiciousEnderman.getJokingTarget();
            if(suspiciousEnderman.distanceTo(jokingTarget) > 4) {
                tick=0;
                return;
            }
            suspiciousEnderman.moveTo(jokingTarget.position());
            joke.doJoke();
            Pretender.LOGGER.info("Joke start");
        }else if(tick > 30){
            this.suspiciousEnderman.jokeComp();
            this.stop();
        }
    }

    @Override
    public void stop() {
        joke.reset();
    }



}
