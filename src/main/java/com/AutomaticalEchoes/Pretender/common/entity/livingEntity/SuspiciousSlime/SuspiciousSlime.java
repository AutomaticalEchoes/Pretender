package com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousSlime;

import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousSlime.Goal.*;
import com.AutomaticalEchoes.Pretender.common.entity.projectile.AcidityBall;
import com.AutomaticalEchoes.Pretender.config.ModCommonConfig;
import com.AutomaticalEchoes.Pretender.register.EntityRegister;
import com.AutomaticalEchoes.Pretender.register.ItemsRegister;
import com.google.common.annotations.VisibleForTesting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Optional;

public class SuspiciousSlime extends Mob implements Enemy {
    private static final EntityDataAccessor<Integer> ID_SIZE = SynchedEntityData.defineId(Slime.class, EntityDataSerializers.INT);
    public static final int MIN_SIZE = 1;
    public static final int MAX_SIZE = 127;
    private final int TRANSLATE_TICK=20 * ModCommonConfig.SUSPICIOUS_SLIME_TRANSLATE_TICK.get();
    private final int PREPARE_TICK= 20 * ModCommonConfig.SUSPICIOUS_SLIME_PREPARE_TICK.get();
    private boolean wasOnGround;
    private int prepareTick = 0;
    private SimpleContainer container =new SimpleContainer(1);
    private int lastHurtTime=TRANSLATE_TICK;
    public float targetSquish;
    public float squish;
    public float oSquish;

    public SuspiciousSlime(EntityType<? extends SuspiciousSlime> p_33588_, Level p_33589_) {
        super(p_33588_, p_33589_);
        this.moveControl=new IMoveControl(this);
        resetLastHurtTime();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ID_SIZE, 1);
    }

    @VisibleForTesting
    public void setSize(int p_33594_, boolean p_33595_) {
        int i = Mth.clamp(p_33594_, 1, 127);
        this.entityData.set(ID_SIZE, i);
        this.reapplyPosition();
        this.refreshDimensions();
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((double)(i * i));
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((double)(0.2F + 0.1F * (float)i));
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue((double)i);
        if (p_33595_) {
            this.setHealth(this.getMaxHealth());
        }
        this.xpReward = i;
    }

    public int getSize() {
        return this.entityData.get(ID_SIZE);
    }

    public void addAdditionalSaveData(CompoundTag p_33619_) {
        super.addAdditionalSaveData(p_33619_);
        p_33619_.putInt("Size", this.getSize() - 1);
        p_33619_.putBoolean("wasOnGround", this.wasOnGround);
    }

    public void readAdditionalSaveData(CompoundTag p_33607_) {
        this.setSize(p_33607_.getInt("Size") + 1, false);
        super.readAdditionalSaveData(p_33607_);
        this.wasOnGround = p_33607_.getBoolean("wasOnGround");
    }

    public boolean isTiny() {
        return this.getSize() <= 1;
    }

    protected ParticleOptions getParticleType() {
        return ParticleTypes.ITEM_SLIME;
    }

    protected boolean shouldDespawnInPeaceful() {
        return this.getSize() > 0;
    }
    @Override
    public void tick() {
        this.squish += (this.targetSquish - this.squish) * 0.5F;
        this.oSquish = this.squish;
        super.tick();
        if (this.onGround && !this.wasOnGround) {
            int i = this.getSize();

            if (spawnCustomParticles()) i = 0; // don't spawn particles if it's handled by the implementation itself
            for(int j = 0; j < i * 8; ++j) {
                float f = this.random.nextFloat() * ((float)Math.PI * 2F);
                float f1 = this.random.nextFloat() * 0.5F + 0.5F;
                float f2 = Mth.sin(f) * (float)i * 0.5F * f1;
                float f3 = Mth.cos(f) * (float)i * 0.5F * f1;
                this.level.addParticle(this.getParticleType(), this.getX() + (double)f2, this.getY(), this.getZ() + (double)f3, 0.0D, 0.0D, 0.0D);
            }

            this.playSound(this.getSquishSound(), this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) / 0.8F);
            this.targetSquish = -0.5F;
        } else if (!this.onGround && this.wasOnGround) {
            this.targetSquish = 1.0F;
        }

        this.wasOnGround = this.onGround;
        this.decreaseSquish();


        if(!this.level.isClientSide){
            if(this.getSize() < 4){
                if(lastHurtTime > 0) {
                    lastHurtTime--;
                }else if( lastHurtTime==0) {
                    translate();
                }
            }else  if(this.getSize() ==4){
                if(prepareTick < PREPARE_TICK){
                    prepareTick++;
                }else if( prepareTick == PREPARE_TICK){
                    this.container.addItem(new ItemStack(Items.ARROW));
                }
            }
        }
    }

    protected void decreaseSquish() {
        this.targetSquish *= 0.6F;
    }

    public int getJumpDelay() {
        return this.random.nextInt(20) + 10;
    }

    public void refreshDimensions() {
        double d0 = this.getX();
        double d1 = this.getY();
        double d2 = this.getZ();
        super.refreshDimensions();
        this.setPos(d0, d1, d2);
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> p_33609_) {
        if (ID_SIZE.equals(p_33609_)) {
            this.refreshDimensions();
            this.setYRot(this.yHeadRot);
            this.yBodyRot = this.yHeadRot;
            if (this.isInWater() && this.random.nextInt(20) == 0) {
                this.doWaterSplashEffect();
            }
        }

        super.onSyncedDataUpdated(p_33609_);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new IFloatGoal(this));
        this.goalSelector.addGoal(2, new AttackGoal(this));
        this.goalSelector.addGoal(4, new RandomDirectionGoal(this));
        this.goalSelector.addGoal(5, new ShootAcidity(this));
        this.goalSelector.addGoal(6, new KeepJumpingGoal(this));
        this.goalSelector.addGoal(6, new EscapeWhileSmallGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, (p_33641_) -> Math.abs(p_33641_.getY() - this.getY()) <= 4.0D));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }


    public void shootAcidity(){
        AcidityBall acidity=new AcidityBall(this.level,this);
        LivingEntity target=this.getTarget();
        this.getJumpControl().jump();
        Vec3 vec3=this.position();
        acidity.setPos(vec3.x,vec3.y+1.0D, vec3.z);
        this.level.addFreshEntity(acidity);
        acidity.setMoveLand(target.position().subtract(this.position()).scale(0.3));
        this.getContainer().getItem(0).setCount(0);
        this.translate();
    }

    public void translate(){
        int i=this.getSize()<4? this.getSize() << 1:1;
        Component component = this.getCustomName();
        SuspiciousSlime slime = EntityRegister.SUSPICIOUS_SLIME.get().create(this.level);
        if (this.isPersistenceRequired()) {
            slime.setPersistenceRequired();
        }
        slime.setCustomName(component);
        slime.setSize(i, true);
        slime.setPos(this.position());
        this.level.addFreshEntity(slime);
        this.discard();
    }


    @Override
    protected void actuallyHurt(DamageSource p_21240_, float p_21241_) {
        super.actuallyHurt(p_21240_, p_21241_);
        resetLastHurtTime();

    }

    private void resetLastHurtTime(){
       if(lastHurtTime!=0) lastHurtTime=TRANSLATE_TICK;
    }


    @Override
    public void remove(RemovalReason p_149847_) {
        int i = this.getSize();
        if (!this.level.isClientSide && i > 1 && !this.container.isEmpty() && this.isDeadOrDying()) {
            Component component = this.getCustomName();
            boolean flag = this.isNoAi();
            float f = (float)i / 4.0F;
            int j = i / 2;
            int k = 2 + this.random.nextInt(3);
            for(int l = 0; l < k; ++l) {
                float f1 = ((float)(l % 2) - 0.5F) * f;
                float f2 = ((float)(l / 2) - 0.5F) * f;
                SuspiciousSlime slime = EntityRegister.SUSPICIOUS_SLIME.get().create(this.level);
                if (this.isPersistenceRequired()) {
                    slime.setPersistenceRequired();
                }
                setContainer(this.getContainer());
                slime.setCustomName(component);
                slime.setNoAi(flag);
                slime.setInvulnerable(this.isInvulnerable());
                slime.setSize(j, true);
                slime.moveTo(this.getX() + (double)f1, this.getY() + 0.5D, this.getZ() + (double)f2, this.random.nextFloat() * 360.0F, 0.0F);
                this.level.addFreshEntity(slime);
            }
        }

        this.setRemoved(p_149847_);
        this.invalidateCaps();
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    public SimpleContainer getContainer() {
        return container;
    }

    public void setContainer(SimpleContainer container){
        this.container=container;
    }

    public void push(Entity p_33636_) {
        super.push(p_33636_);
        if (p_33636_ instanceof IronGolem && this.isDealsDamage()) {
            this.dealDamage((LivingEntity)p_33636_);
        }

    }

    public void playerTouch(Player p_33611_) {
        if (this.isDealsDamage()) {
            this.dealDamage(p_33611_);
        }

    }

    protected void dealDamage(LivingEntity p_33638_) {
        if (this.isAlive()) {
            int i = this.getSize();
            if (this.distanceToSqr(p_33638_) < 0.6D * (double)i * 0.6D * (double)i && this.hasLineOfSight(p_33638_) && p_33638_.hurt(DamageSource.mobAttack(this), this.getAttackDamage())) {
                this.playSound(SoundEvents.SLIME_ATTACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                this.doEnchantDamageEffects(this, p_33638_);
            }
        }

    }

    protected float getStandingEyeHeight(Pose p_33614_, EntityDimensions p_33615_) {
        return 0.625F * p_33615_.height;
    }

    public boolean isDealsDamage() {
        return !this.isTiny() && this.isEffectiveAi();
    }

    protected float getAttackDamage() {
        return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    protected SoundEvent getHurtSound(DamageSource p_33631_) {
        return this.isTiny() ? SoundEvents.SLIME_HURT_SMALL : SoundEvents.SLIME_HURT;
    }

    protected SoundEvent getDeathSound() {
        return this.isTiny() ? SoundEvents.SLIME_DEATH_SMALL : SoundEvents.SLIME_DEATH;
    }

    protected SoundEvent getSquishSound() {
        return this.isTiny() ? SoundEvents.SLIME_SQUISH_SMALL : SoundEvents.SLIME_SQUISH;
    }

    public static boolean checkSlimeSpawnRules(EntityType<Slime> p_219113_, LevelAccessor p_219114_, MobSpawnType p_219115_, BlockPos p_219116_, RandomSource p_219117_) {
        if (p_219114_.getDifficulty() != Difficulty.PEACEFUL) {
            if (p_219114_.getBiome(p_219116_).is(BiomeTags.ALLOWS_SURFACE_SLIME_SPAWNS) && p_219116_.getY() > 50 && p_219116_.getY() < 70 && p_219117_.nextFloat() < 0.5F && p_219117_.nextFloat() < p_219114_.getMoonBrightness() && p_219114_.getMaxLocalRawBrightness(p_219116_) <= p_219117_.nextInt(8)) {
                return checkMobSpawnRules(p_219113_, p_219114_, p_219115_, p_219116_, p_219117_);
            }

            if (!(p_219114_ instanceof WorldGenLevel)) {
                return false;
            }

            ChunkPos chunkpos = new ChunkPos(p_219116_);
            boolean flag = WorldgenRandom.seedSlimeChunk(chunkpos.x, chunkpos.z, ((WorldGenLevel)p_219114_).getSeed(), 987234911L).nextInt(10) == 0;
            if (p_219117_.nextInt(10) == 0 && flag && p_219116_.getY() < 40) {
                return checkMobSpawnRules(p_219113_, p_219114_, p_219115_, p_219116_, p_219117_);
            }
        }

        return false;
    }

    public float getSoundVolume() {
        return 0.4F * (float)this.getSize();
    }

    public int getMaxHeadXRot() {
        return 0;
    }

    public boolean doPlayJumpSound() {
        return this.getSize() > 0;
    }

    protected void jumpFromGround() {
        Vec3 vec3 = this.getDeltaMovement();
        this.setDeltaMovement(vec3.x, (double)this.getJumpPower(), vec3.z);
        this.hasImpulse = true;
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_33601_, DifficultyInstance p_33602_, MobSpawnType p_33603_, @Nullable SpawnGroupData p_33604_, @Nullable CompoundTag p_33605_) {
        this.setSize(1, true);
        return super.finalizeSpawn(p_33601_, p_33602_, p_33603_, p_33604_, p_33605_);
    }

    public float getSoundPitch() {
        float f = this.isTiny() ? 1.4F : 0.8F;
        return ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * f;
    }

    public SoundEvent getJumpSound() {
        return this.isTiny() ? SoundEvents.SLIME_JUMP_SMALL : SoundEvents.SLIME_JUMP;
    }

    public EntityDimensions getDimensions(Pose p_33597_) {
        return super.getDimensions(p_33597_).scale(0.255F * (float)this.getSize());
    }

    /**
     * Called when the slime spawns particles on landing, see onUpdate.
     * Return true to prevent the spawning of the default particles.
     */
    protected boolean spawnCustomParticles() { return false; }

    public InteractionResult mobInteract(Player p_28298_, InteractionHand p_28299_) {
        ItemStack itemstack = p_28298_.getItemInHand(p_28299_);
        if(itemstack.is(Items.BUCKET) &&  this.getSize() ==4) {
            p_28298_.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
            ItemStack itemstack1 = ItemUtils.createFilledResult(itemstack, p_28298_, ItemsRegister.ACIDITY_BUCKET.get().getDefaultInstance());
            p_28298_.setItemInHand(p_28299_, itemstack1);
            this.translate();
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }else if(itemstack.is(Items.WATER_BUCKET) && this.getSize() !=4){
            p_28298_.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
            ItemStack itemstack1 = ItemUtils.createFilledResult(itemstack, p_28298_, this.getSize() !=1 ? ItemsRegister.MUCUS_BUCKET.get().getDefaultInstance() : ItemsRegister.SUSPICIOUS_WATER_BUCKET.get().getDefaultInstance());
            p_28298_.setItemInHand(p_28299_, itemstack1);
            this.discard();
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }else {
            return super.mobInteract(p_28298_, p_28299_);
        }
    }

}
