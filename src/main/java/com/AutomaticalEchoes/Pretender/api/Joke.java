package com.AutomaticalEchoes.Pretender.api;

import com.AutomaticalEchoes.Pretender.api.JokeCase;
import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousEnderman.SuspiciousEnderman;
import net.minecraft.world.entity.LivingEntity;

public abstract class Joke<T extends LivingEntity> implements JokeCase<T> {
    protected SuspiciousEnderman suspiciousEnderman;
    protected Case JokeCase;

    public Joke(SuspiciousEnderman suspiciousEnderman) {
        this.suspiciousEnderman = suspiciousEnderman;
    }
}
