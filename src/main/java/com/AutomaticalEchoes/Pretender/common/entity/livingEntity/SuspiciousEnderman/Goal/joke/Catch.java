package com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousEnderman.Goal.joke;

import com.AutomaticalEchoes.Pretender.api.Joke;
import com.AutomaticalEchoes.Pretender.api.JokeCase;
import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousEnderman.SuspiciousEnderman;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class Catch extends Joke<LivingEntity> {
    public Catch(SuspiciousEnderman suspiciousEnderman) {
        super(suspiciousEnderman);
    }

    @Override
    public Case Case() {
        return Case.EMPTY;
    }

    @Override
    public boolean canJoke() {
        return false;
    }

    @Override
    public void doJoke(LivingEntity target) {
        target.startRiding(suspiciousEnderman);
    }

    @Override
    public @Nullable Predicate<LivingEntity> TargetSelector() {
        return null;
    }

}
