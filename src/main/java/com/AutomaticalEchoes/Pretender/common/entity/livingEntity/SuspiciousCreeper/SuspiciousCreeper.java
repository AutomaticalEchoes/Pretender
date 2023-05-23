package com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousCreeper;

import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousCreeper.Goals.SwellGoal;
import com.AutomaticalEchoes.Pretender.config.ModCommonConfig;
import com.AutomaticalEchoes.Pretender.register.EffectsRegister;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;

public class SuspiciousCreeper extends Monster implements PowerableMob {
    private static final EntityDataAccessor<Integer> DATA_SWELL_DIR = SynchedEntityData.defineId(SuspiciousCreeper.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_IS_POWERED = SynchedEntityData.defineId(SuspiciousCreeper.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_IS_IGNITED = SynchedEntityData.defineId(SuspiciousCreeper.class, EntityDataSerializers.BOOLEAN);
    private int oldSwell;
    private int swell;
    private int maxSwell = ModCommonConfig.SUSPICIOUS_CREEPER_INJECT_TIME.get()*20;
    private int explosionRadius = ModCommonConfig.SUSPICIOUS_CREEPER_EXPLODE_RADIUS.get();
    private int areaEffectRadius= ModCommonConfig.SUSPICIOUS_CREEPER_AREA_EFFECT_RADIUS.get();
    private int effectDurationTime= ModCommonConfig.SUSPICIOUS_CREEPER_RAGE_EFFECT_DURATION_TIME.get()*20;
    private int areaEffectDurationTime= ModCommonConfig.SUSPICIOUS_CREEPER_AREA_EFFECT_DURATION_TIME.get()*20;
    private int areaEffectWaitTime= 1;
    private int droppedSkulls;

    public SuspiciousCreeper(EntityType<? extends SuspiciousCreeper> p_32278_, Level p_32279_) {
        super(p_32278_, p_32279_);
    }


    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new SwellGoal(this));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Ocelot.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Cat.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    public int getMaxFallDistance() {
        return this.getTarget() == null ? 3 : 3 + (int)(this.getHealth() - 1.0F);
    }

    public boolean causeFallDamage(float p_149687_, float p_149688_, DamageSource p_149689_) {
        boolean flag = super.causeFallDamage(p_149687_, p_149688_, p_149689_);
        this.swell += (int)(p_149687_ * 1.5F);
        if (this.swell > this.maxSwell - 5) {
            this.swell = this.maxSwell - 5;
        }

        return flag;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_SWELL_DIR, -1);
        this.entityData.define(DATA_IS_POWERED, false);
        this.entityData.define(DATA_IS_IGNITED, false);
    }

    public void addAdditionalSaveData(CompoundTag p_32304_) {
        super.addAdditionalSaveData(p_32304_);
        if (this.entityData.get(DATA_IS_POWERED)) {
            p_32304_.putBoolean("powered", true);
        }

        p_32304_.putShort("Fuse", (short)this.maxSwell);
        p_32304_.putByte("ExplosionRadius", (byte)this.explosionRadius);
        p_32304_.putByte("AreaEffectWaitTime", (byte)this.areaEffectWaitTime);
        p_32304_.putByte("EffectDurationTime", (byte)this.effectDurationTime);
        p_32304_.putBoolean("ignited", this.isIgnited());
    }

    public void readAdditionalSaveData(CompoundTag p_32296_) {
        super.readAdditionalSaveData(p_32296_);
        this.entityData.set(DATA_IS_POWERED, p_32296_.getBoolean("powered"));
        if (p_32296_.contains("Fuse", 99)) {
            this.maxSwell = p_32296_.getShort("Fuse");
        }

        if (p_32296_.contains("ExplosionRadius", 99)) {
            this.explosionRadius = p_32296_.getByte("ExplosionRadius");
        }
        if (p_32296_.contains("AreaEffectWaitTime", 99)) {
            this.explosionRadius = p_32296_.getByte("AreaEffectWaitTime");
        }
        if (p_32296_.contains("EffectDurationTime", 99)) {
            this.explosionRadius = p_32296_.getByte("EffectDurationTime");
        }

        if (p_32296_.getBoolean("ignited")) {
            this.ignite();
        }

    }

    public void tick() {
        if (this.isAlive()) {
            this.oldSwell = this.swell;
            if (this.isIgnited()) {
                this.setSwellDir(1);
            }

            int i = this.getSwellDir();
            Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED))
                    .setBaseValue( i>0 ? 0.0D : 0.25D);
            if (i > 0 && this.swell == 0) {
                this.playSound(SoundEvents.CREEPER_PRIMED, 1.0F, 0.5F);
                this.gameEvent(GameEvent.PRIME_FUSE);
            }

            this.swell += i;
            if (this.swell < 0) {
                this.swell = 0;
            }


            if (this.swell >= this.maxSwell) {
                this.swell = 0;
                this.entityData.set(DATA_IS_IGNITED,false);
                this.setSwellDir(-1);
            }

        }

        super.tick();
    }

    public void setTarget(@Nullable LivingEntity p_149691_) {
        if (!(p_149691_ instanceof Goat)) {
            super.setTarget(p_149691_);
        }
    }

    protected SoundEvent getHurtSound(DamageSource p_32309_) {
        return SoundEvents.CREEPER_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.CREEPER_DEATH;
    }

    protected void dropCustomDeathLoot(DamageSource p_32292_, int p_32293_, boolean p_32294_) {
        super.dropCustomDeathLoot(p_32292_, p_32293_, p_32294_);
        Entity entity = p_32292_.getEntity();
        if (entity != this && entity instanceof SuspiciousCreeper creeper) {
            if (creeper.canDropMobsSkull()) {
                creeper.increaseDroppedSkulls();
                this.spawnAtLocation(Items.CREEPER_HEAD);
            }
        }

    }

    @Override
    protected void actuallyHurt(DamageSource p_21240_, float p_21241_) {
        super.actuallyHurt(p_21240_, p_21241_);
        if(!this.isIgnited()){
            ignite();
        } else if (swell <= maxSwell && swell > 0) {
            explodeBoomer();
        }
    }

    public boolean doHurtTarget(Entity p_32281_) {
        return true;
    }

    public boolean isPowered() {
        return this.entityData.get(DATA_IS_POWERED);
    }

    public float getSwelling(float p_32321_) {
        return Mth.lerp(p_32321_, (float)this.oldSwell, (float)this.swell) / (float)(this.maxSwell - 2);
    }

    public int getSwellDir() {
        return this.entityData.get(DATA_SWELL_DIR);
    }

    public void setSwellDir(int p_32284_) {
        this.entityData.set(DATA_SWELL_DIR, p_32284_);
    }

    public void thunderHit(ServerLevel p_32286_, LightningBolt p_32287_) {
        super.thunderHit(p_32286_, p_32287_);
        this.entityData.set(DATA_IS_POWERED, true);
    }

    protected InteractionResult mobInteract(Player p_32301_, InteractionHand p_32302_) {
        ItemStack itemstack = p_32301_.getItemInHand(p_32302_);
        if (itemstack.is(Items.FLINT_AND_STEEL)) {
            this.level.playSound(p_32301_, this.getX(), this.getY(), this.getZ(), SoundEvents.FLINTANDSTEEL_USE, this.getSoundSource(), 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
            if (!this.level.isClientSide) {
                this.ignite();
                itemstack.hurtAndBreak(1, p_32301_, (p_32290_) -> {
                    p_32290_.broadcastBreakEvent(p_32302_);
                });
            }

            return InteractionResult.sidedSuccess(this.level.isClientSide);
        } else {
            return super.mobInteract(p_32301_, p_32302_);
        }
    }

    public void explodeBoomer() {
        if (!this.level.isClientSide) {
            float f = this.isPowered() ? 2.0F : 1.0F;
            this.dead = true;
            this.level.explode(this, this.getX(), this.getY(), this.getZ(), (float)this.explosionRadius * f, Explosion.BlockInteraction.NONE);
            this.discard();
            this.spawnLingeringCloud();
        }

    }

    private void spawnLingeringCloud() {
        Collection<MobEffectInstance> collection = this.getActiveEffects();
            AreaEffectCloud areaeffectcloud = new AreaEffectCloud(this.level, this.getX(), this.getY(), this.getZ());
            areaeffectcloud.setRadius(areaEffectRadius);
            areaeffectcloud.setRadiusOnUse(-0.5F);
            areaeffectcloud.setWaitTime(areaEffectWaitTime);
            areaeffectcloud.setDuration(areaEffectDurationTime);
            areaeffectcloud.setRadiusPerTick(-areaeffectcloud.getRadius() / (float)(areaEffectDurationTime));

            for(MobEffectInstance mobeffectinstance : collection) {
                areaeffectcloud.addEffect(new MobEffectInstance(mobeffectinstance));
            }
            areaeffectcloud.addEffect(new MobEffectInstance(EffectsRegister.RAGE_TARGET.get(),effectDurationTime));
            areaeffectcloud.addEffect(new MobEffectInstance(MobEffects.GLOWING,effectDurationTime));

            this.level.addFreshEntity(areaeffectcloud);

    }

    public boolean isIgnited() {
        return this.entityData.get(DATA_IS_IGNITED);
    }

    public void ignite() {
        this.entityData.set(DATA_IS_IGNITED, true);
    }

    public boolean canDropMobsSkull() {
        return this.isPowered() && this.droppedSkulls < 1;
    }

    public void increaseDroppedSkulls() {
        ++this.droppedSkulls;
    }

    public int getSwell() {
        return swell;
    }

    public int getExplosionRadius() {
        return explosionRadius;
    }

    public int getEffectDurationTime() {
        return effectDurationTime;
    }

    public int getAreaEffectDurationTime() {
        return areaEffectDurationTime;
    }

    public int getAreaEffectWaitTime() {
        return areaEffectWaitTime;
    }

}
