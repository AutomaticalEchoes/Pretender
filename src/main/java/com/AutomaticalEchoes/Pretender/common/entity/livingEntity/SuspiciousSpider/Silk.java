package com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousSpider;

import com.AutomaticalEchoes.Pretender.common.CommonModEvents;
import com.AutomaticalEchoes.Pretender.common.netWork.packet.PoseChange;
import com.AutomaticalEchoes.Pretender.register.EntityRegister;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkDirection;

import javax.annotation.Nullable;

public class Silk extends AbstractArrow {
    private static final EntityDataAccessor<Integer> ID_HIT_TARGET = SynchedEntityData.defineId(Silk.class, EntityDataSerializers.INT);
    private LivingEntity hitEntity = null;
    public static Silk Create(EntityType<? extends AbstractArrow> p_36721_, Level p_36722_){
        return new Silk(EntityRegister.SILK.get(),p_36722_);
    }

    protected Silk(EntityType<? extends AbstractArrow> p_36721_, Level p_36722_) {
        super(EntityRegister.SILK.get(), p_36722_);
    }
    public Silk(Level level, LivingEntity owner){super(EntityRegister.SILK.get(),owner,level);}
    @Override
    protected ItemStack getPickupItem() {
        return null;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ID_HIT_TARGET, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag p_36772_) {
        super.addAdditionalSaveData(p_36772_);
        p_36772_.putInt("id_hit_target",this.entityData.get(ID_HIT_TARGET));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag p_36761_) {
        super.readAdditionalSaveData(p_36761_);
        if(p_36761_.contains("id_hit_target")) this.entityData.set(ID_HIT_TARGET, p_36761_.getInt("id_hit_target"));
    }

    @Override
    protected void onHitEntity(EntityHitResult p_36757_) {
        DamageSource damageSource=DamageSource.arrow(this,getOwner());
        int i = Mth.ceil(Mth.clamp( this.getBaseDamage(), 0.0D, 2.147483647E9D));
        if(p_36757_.getEntity() instanceof LivingEntity livingEntity){
            if(!this.level.isClientSide){
                if (livingEntity.hurt(damageSource,i)) {
                    this.hitEntity = livingEntity;
                    this.entityData.set(ID_HIT_TARGET,hitEntity.getId());
                }
            }
            this.setDeltaMovement(0,0,0);
        }
    }

    @Override
    public void tick() {
        if ( this.getOwner()==null || !this.getOwner().isAlive())
            this.discard();
        if ( this.getHitEntity()!=null && !this.isVehicle()){
            this.startRiding(getHitEntity());
        }
        super.tick();
    }

    @Override
    public void remove(RemovalReason p_146834_) {
        if ( getHitEntity() instanceof ServerPlayer player && player.getForcedPose()==Pose.SLEEPING){
            player.setForcedPose(null);
            player.setPose(Pose.STANDING);
            CommonModEvents.CHANNEL.sendTo(new PoseChange(0),player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        }
        super.remove(p_146834_);
    }


    public @Nullable LivingEntity getHitEntity() {
        Entity entity = this.level.getEntity(this.entityData.get(ID_HIT_TARGET));
        if (entity instanceof LivingEntity target) {
            return target;
        } else {
            return null;
        }
    }


    @Override
    protected void tickDespawn() {
        super.tickDespawn();
    }

    public void setHitEntity(LivingEntity hitEntity) {
        this.hitEntity = hitEntity;
    }

    @Override
    public boolean shouldRender(double p_20296_, double p_20297_, double p_20298_) {
        double d3=9999;
        if(this.getOwner()!=null){
            double d0 = this.getOwner().getX() - p_20296_;
            double d1 = this.getOwner().getY() - p_20297_;
            double d2 = this.getOwner().getZ() - p_20298_;
            d3 = d0 * d0 + d1 * d1 + d2 * d2;
        }

        return this.shouldRenderAtSqrDistance(d3) || super.shouldRender(p_20296_, p_20297_, p_20298_);
    }
}
