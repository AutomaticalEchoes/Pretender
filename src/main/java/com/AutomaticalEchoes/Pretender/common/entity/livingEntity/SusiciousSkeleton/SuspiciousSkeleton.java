package com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SusiciousSkeleton;

import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SusiciousSkeleton.Goal.FakeBowAttackGoal;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class SuspiciousSkeleton extends Skeleton {
    private final FakeBowAttackGoal<AbstractSkeleton> fakeBowGoal = new FakeBowAttackGoal<>(this, 1.0D, 20, 15.0F);
    private final MeleeAttackGoal meleeAttackGoal = new MeleeAttackGoal(this, 1.2D, false) {
        public void stop() {
            super.stop();
            mob.setAggressive(false);
        }

        public void start() {
            super.start();
            mob.setAggressive(true);
        }
    };
    private boolean isBow = false;

    public SuspiciousSkeleton(EntityType<? extends Skeleton> p_32133_, Level p_32134_) {
        super(p_32133_, p_32134_);
        this.IReassessWeaponGoal();
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_32146_, DifficultyInstance p_32147_, MobSpawnType p_32148_, @Nullable SpawnGroupData p_32149_, @Nullable CompoundTag p_32150_) {
        p_32149_ =  super.finalizeSpawn(p_32146_, p_32147_, p_32148_, p_32149_, p_32150_);
        this.IReassessWeaponGoal();
        return p_32149_;
    }

    public void IReassessWeaponGoal(){
        if (!this.level.isClientSide) {
            this.goalSelector.removeGoal(this.fakeBowGoal);
            this.goalSelector.removeGoal(this.meleeAttackGoal);
            ItemStack itemstack = this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, item -> item instanceof net.minecraft.world.item.BowItem));
            if (itemstack.is(Items.BOW)) {
                int i = 20;
                if (this.level.getDifficulty() != Difficulty.HARD) {
                    i = 40;
                }
                this.fakeBowGoal.setMinAttackInterval(i);
                this.isBow = true;
            }
            this.goalSelector.addGoal(4,isBow? fakeBowGoal : meleeAttackGoal);
        }
    }

    @Override
    public void performRangedAttack(LivingEntity p_32141_, float p_32142_) {
        ItemStack itemstack = this.getProjectile(this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, item -> item instanceof net.minecraft.world.item.BowItem)));
        AbstractArrow abstractarrow = this.getArrow(itemstack, p_32142_);
        if (this.getMainHandItem().getItem() instanceof net.minecraft.world.item.BowItem)
            abstractarrow = ((net.minecraft.world.item.BowItem)this.getMainHandItem().getItem()).customArrow(abstractarrow);
        double d0 = p_32141_.getX() - this.getX();
        double d1 = p_32141_.getY(0.3333333333333333D) - abstractarrow.getY();
        double d2 = p_32141_.getZ() - this.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        abstractarrow.setBaseDamage(this.getHealth() * this.level.getDifficulty().getId());
        abstractarrow.setPierceLevel((byte) 10);
        abstractarrow.shoot(d0, d1 + d3 * (double)0.2F, d2, 1.6F, (float)(14 - this.level.getDifficulty().getId() * 4));
        this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level.addFreshEntity(abstractarrow);
        this.die(DamageSource.arrow(abstractarrow,this));
    }

    @Override
    public void reassessWeaponGoal() {
    }


}
