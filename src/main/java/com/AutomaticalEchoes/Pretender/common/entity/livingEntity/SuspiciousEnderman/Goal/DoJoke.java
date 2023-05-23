package com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousEnderman.Goal;

import com.AutomaticalEchoes.Pretender.Pretender;
import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousEnderman.SuspiciousEnderman;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class DoJoke extends Goal {
    private final SuspiciousEnderman suspiciousEnderman;
    private @Nullable BlockPos.MutableBlockPos angryJokePos = null;
    private @Nullable Integer SelectedStructuresID = null;
    private int tick =0 ;

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
        if(this.suspiciousEnderman.isAngry() && this.suspiciousEnderman.level instanceof ServerLevel serverLevel){
            if(SelectedStructuresID ==null){
                int i = Pretender.RANDOM.nextInt(SuspiciousEnderman.STRUCTURES.length + 1);
                angryJokePos = i!=0? suspiciousEnderman.PreparePos(serverLevel,i) : null;
            }else {
                angryJokePos = suspiciousEnderman.PreparePos(serverLevel,SelectedStructuresID);
            }
        }

        this.tick = 0;
    }

    public void startWith(@Nullable Integer selectedStructuresID){
        this.SelectedStructuresID = selectedStructuresID;
        this.start();
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
            if(jokingTarget instanceof Player player){
                jokeWithPlayer(player);
            }else {
                jokeWithAiLiving(jokingTarget);
            }
        }else if(tick > 30){
            this.suspiciousEnderman.jokeComp();
            this.stop();
        }
    }

    @Override
    public void stop() {
        suspiciousEnderman.reset();
        this.SelectedStructuresID = null;
        this.angryJokePos = null;
    }

    private void jokeWithPlayer(Player player){
        if(suspiciousEnderman.angryJoke(player,this.angryJokePos)) return;


        if(suspiciousEnderman.CarryNothing()){
            suspiciousEnderman.steal(player);
        }else if(!suspiciousEnderman.isCarryItemEmpty()){
            suspiciousEnderman.frameUp(player);
        }else if(!suspiciousEnderman.getPassengers().isEmpty()){
            Entity firstPassenger = suspiciousEnderman.getFirstPassenger();
            suspiciousEnderman.stopCatching();
            if(!(firstPassenger instanceof Monster) && firstPassenger instanceof LivingEntity) player.startRiding(firstPassenger);
        }
    }

    private void jokeWithAiLiving(LivingEntity livingEntity){
        if(suspiciousEnderman.CarryNothing()){
            livingEntity.startRiding(suspiciousEnderman);
        }else if(livingEntity instanceof Creeper creeper && suspiciousEnderman.getFirstPassenger() instanceof Cat cat){
            cat.startRiding(creeper);
        }else if(livingEntity instanceof Skeleton skeleton && suspiciousEnderman.getFirstPassenger() instanceof Wolf wolf){
            wolf.startRiding(skeleton);
        }else if(suspiciousEnderman.isCarryItemEmpty()){
            suspiciousEnderman.stopCatching();
        }else {
            suspiciousEnderman.dropCarried();
        }
    }


}
