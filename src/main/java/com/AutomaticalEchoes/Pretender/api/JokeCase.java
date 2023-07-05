package com.AutomaticalEchoes.Pretender.api;

import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public interface JokeCase<T extends LivingEntity> {
    Case Case();
    boolean canJoke();
    void doJoke(T target);
    @Nullable Predicate<LivingEntity> TargetSelector();

    enum Case{
        EMPTY(),
        CATCHING(),
        CARRIED();
    }

}
