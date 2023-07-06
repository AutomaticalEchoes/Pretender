package com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousEnderman.Goal;

import com.AutomaticalEchoes.Pretender.Pretender;
import com.AutomaticalEchoes.Pretender.api.JokeCase;
import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousEnderman.Goal.joke.*;
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
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class JokeSelect extends Goal {
    private final SuspiciousEnderman suspiciousEnderman;
    private JokeCase.Case Case = JokeCase.Case.EMPTY;
    public final ArrayList<JokeCase<?>> JOKE_LIST = new ArrayList<>();
    public final Angry ANGRY;
    public final Steal STEAL;
    public final FrameUp FRAME_UP;
    public final Catch CATCH;
    public final Ride CAT_CREEPER;
    public final Ride WOLF_SKELETON;
    public final Ride LIVING_PLAYER;
    public JokeSelect(SuspiciousEnderman suspiciousEnderman) {
        this.setFlags(EnumSet.of(Flag.MOVE));
        this.suspiciousEnderman = suspiciousEnderman;

        ANGRY = new Angry(suspiciousEnderman);
        STEAL = new Steal(suspiciousEnderman);
        FRAME_UP = new FrameUp(suspiciousEnderman);
        CATCH = new Catch(suspiciousEnderman);
        CAT_CREEPER = new Ride(suspiciousEnderman , Ride::CatWithCreeper);
        WOLF_SKELETON = new Ride(suspiciousEnderman,Ride::WolfWithSkeleton);
        LIVING_PLAYER = new Ride(suspiciousEnderman,Ride::LivingWithPlayer);

        JOKE_LIST.add(ANGRY);
        JOKE_LIST.add(STEAL);
        JOKE_LIST.add(FRAME_UP);
        JOKE_LIST.add(CATCH);
        JOKE_LIST.add(CAT_CREEPER);
        JOKE_LIST.add(WOLF_SKELETON);
        JOKE_LIST.add(LIVING_PLAYER);
    }


    public JokeSelect AddJoke(JokeCase<?> jokeCase){
        JOKE_LIST.add(jokeCase);
        return this;
    }

    @Override
    public boolean canUse() {
        return suspiciousEnderman.isClown() && !suspiciousEnderman.isJoking();
    }

    @Override
    public void start() {
        CheckCase();
        if(SelectJoke()){
            suspiciousEnderman.StartJoke();
        }else {
            Reset();
        }
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



    public boolean SelectJoke(){
        List<JokeCase<?>> jokeCases = JOKE_LIST.stream().filter(this::Rule).toList();
        int size = jokeCases.size();
        if(size >0){
            int i = Pretender.RANDOM.nextInt(size);
            suspiciousEnderman.setJoke( jokeCases.get(i) );
            return true;
        }
        return false;
    }

    public void Reset(){
        suspiciousEnderman.reset();
        Case = JokeCase.Case.EMPTY;
    }

    public boolean Rule(JokeCase<?> joke){
        return joke.Case() == this.Case && joke.canJoke();
    }
}
