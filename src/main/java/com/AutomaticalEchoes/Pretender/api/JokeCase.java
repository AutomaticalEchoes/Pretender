package com.AutomaticalEchoes.Pretender.api;

import net.minecraft.world.entity.LivingEntity;

public interface JokeCase<T extends LivingEntity> {
    int Level();
    boolean canJoke();
    void doJoke(T target);
    T Target();

}
