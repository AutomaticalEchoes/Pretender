package com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousSpider.goal;

import com.AutomaticalEchoes.Pretender.common.CommonModEvents;
import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousSpider.Silk;
import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousSpider.SuspiciousSpider;
import com.AutomaticalEchoes.Pretender.common.netWork.packet.PoseChange;
import com.AutomaticalEchoes.Pretender.config.ModCommonConfig;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraftforge.network.NetworkDirection;

import java.util.EnumSet;

public class SusSpiderAttackGoal extends Goal {
    private final SuspiciousSpider spider;
    private Silk silk = null;
    private final int doHurtTick = 10 * ModCommonConfig.SUSPICIOUS_SPIDER_SILKE_ENTANGLEMENT_DO_HURT_TICK.get();
    private final int prepareTick = 10 * ModCommonConfig.SUSPICIOUS_SPIDER_SILKE_ENTANGLEMENT_PREPAER_TIME.get();
    private int attackTime;
    public SusSpiderAttackGoal(SuspiciousSpider p_32871_) {
        this.spider = p_32871_;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));

    }
    private boolean distanceCheck(LivingEntity target){
        double distanceTo = this.spider.distanceTo(target);
        return  this.spider.hasLineOfSight(target)
                && distanceTo > this.spider.getSilkAttackDistanceMin()
                && distanceTo < this.spider.getSilkAttackDistanceMax();
    }

    public boolean canUse() {
        return this.spider.getTarget() != null
                && this.spider.getContainer().getItem(0).getCount() > 0
                && this.spider.getTarget().isAttackable()
                && this.spider.getTarget().isAlive()
                &&!this.spider.isAttacking()
                && distanceCheck(this.spider.getTarget());
    }

    @Override
    public boolean canContinueToUse() {
        return this.spider.isAttacking();
    }

    public void start() {
        this.spider.setAttacking(true);
        this.spider.getNavigation().stop();
        this.spider.getLookControl().setLookAt(spider.getTarget());
        this.attackTime=0;
//        this.silk=null;
    }

    @Override
    public void tick() {
        attackTime++;
        this.spider.getLookControl().setLookAt(this.spider.getTarget());
        boolean flag = attackTime >= prepareTick;
        if (flag && silk!=null && silk.getHitEntity() instanceof ServerPlayer player && !player.isSleeping()){
            player.setForcedPose(Pose.FALL_FLYING);
            CommonModEvents.CHANNEL.sendTo(new PoseChange(1),player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        }else if(flag && silk == null && this.spider.getLookControl().isLookingAtTarget()){
            this.silk=spider.shootSilk(this.spider.getTarget());
        }

        if (this.silk!=null && this.silk.getVehicle()!=null && this.silk.getVehicle() instanceof LivingEntity livingEntity) {
            if(this.spider.getTarget() != livingEntity)this.spider.setTarget(livingEntity);
            if(attackTime % this.doHurtTick == 0) this.spider.doHurtTarget(livingEntity);
        }
        if (this.silk!=null &&((this.silk.isRemoved() || !this.spider.getTarget().isAlive()) || (attackTime > 100 && !silk.isVehicle()))) stop();
    }

    @Override
    public void stop() {
        this.spider.setClamDown(11*20);
        this.spider.setTarget(null);
        this.spider.setAttacking(false);
        if(this.silk!=null&&this.silk.isAddedToWorld()) {
            if(this.silk.getHitEntity() instanceof ServerPlayer player){
                player.setForcedPose(null);
                CommonModEvents.CHANNEL.sendTo(new PoseChange(0),player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
            }
            this.silk.discard();
            this.silk=null;
        }

        this.attackTime=0;
    }

}
