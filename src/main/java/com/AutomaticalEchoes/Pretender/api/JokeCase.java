package com.AutomaticalEchoes.Pretender.api;

import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousEnderman.SuspiciousEnderman;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public interface JokeCase<T extends LivingEntity> {
    SuspiciousEnderman owner();
    Case Case();
    boolean canJoke();
    void doJoke();
    void reset();
    @Nullable Predicate<LivingEntity> TargetSelector();

    enum Case{
        EMPTY(),
        CATCHING(),
        CARRIED();
    }

}
