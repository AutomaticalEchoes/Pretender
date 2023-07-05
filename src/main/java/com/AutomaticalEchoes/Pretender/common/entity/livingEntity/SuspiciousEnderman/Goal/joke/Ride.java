package com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousEnderman.Goal.joke;

import com.AutomaticalEchoes.Pretender.api.Joke;
import com.AutomaticalEchoes.Pretender.api.JokeCase;
import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousEnderman.SuspiciousEnderman;
import it.unimi.dsi.fastutil.Function;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.lang3.function.TriFunction;
import org.apache.logging.log4j.util.TriConsumer;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;
import java.util.function.Predicate;

public class Ride extends Joke<LivingEntity> {
    public LivingEntity Rider;
    public LivingEntity Saddle;
    private final TriFunction<Ride,LivingEntity,LivingEntity,Boolean> func;
    private final Predicate<LivingEntity> LIVING_ENTITY_PREDICATE  = livingEntity -> {
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
    };
    public Ride(SuspiciousEnderman suspiciousEnderman , TriFunction<Ride,LivingEntity,LivingEntity,Boolean> func) {
        super(suspiciousEnderman);
        this.func = func;
    }


    @Override
    public Case Case() {
        return Case.CATCHING;
    }

    @Override
    public boolean canJoke() {
        if(suspiciousEnderman.getFirstPassenger() instanceof LivingEntity catching){
            return func.apply(this,catching,suspiciousEnderman.getJokingTarget());
        }
        return false;
    }

    @Override
    public void doJoke(LivingEntity target) {
        Rider.startRiding(Saddle);
    }

    @Override
    public @Nullable Predicate<LivingEntity> TargetSelector() {
        return LIVING_ENTITY_PREDICATE;
    }

    public static Boolean CatWithCreeper(Ride rideJoke,LivingEntity catching,LivingEntity target){
        if(catching instanceof Cat && target instanceof Creeper){
            rideJoke.Rider = catching;
            rideJoke.Saddle = target;
            return true;
        }
        return false;
    }
    public static Boolean WolfWithSkeleton(Ride rideJoke,LivingEntity catching,LivingEntity target){
        if(catching instanceof Wolf && target instanceof Skeleton){
            rideJoke.Rider = catching;
            rideJoke.Saddle = target;
            return true;
        }
        return false;
    }
    public static Boolean LivingWithPlayer(Ride rideJoke,LivingEntity catching,LivingEntity target){
        if(!(catching instanceof Monster) && target instanceof Player){
            rideJoke.Rider = target;
            rideJoke.Saddle = catching;
            return true;
        }
        return false;
    }

}
