package com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousSpider;

import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousSpider.goal.SusSpiderAttackGoal;
import com.AutomaticalEchoes.Pretender.config.ModCommonConfig;
import com.AutomaticalEchoes.Pretender.register.EffectsRegister;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SuspiciousSpider extends Spider {
    private boolean isAttacking;
    private SimpleContainer container =new SimpleContainer(new ItemStack(Items.ARROW));
    private int silkAttackDistanceMax = ModCommonConfig.SUSPICIOUS_SPIDER_SILKE_ENTANGLEMENT_DISTANCES_MAX.get();
    private int silkAttackDistanceMin = ModCommonConfig.SUSPICIOUS_SPIDER_SILKE_ENTANGLEMENT_DISTANCES_MIN.get();
    private int targetChangeTime;
    private int remainingPersistentAngerTime;
    private int clamDown;
    private final int harmfulEffectDurationTime = ModCommonConfig.SUSPICIOUS_SPIDER_SILKE_ENTANGLEMENT_DO_HURT_TICK.get() * 20;
    public SuspiciousSpider(EntityType<? extends Spider> p_33786_, Level p_33787_) {
        super(p_33786_, p_33787_);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(4, new SusSpiderAttackGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }


    @Override
    public boolean doHurtTarget(Entity p_21372_) {
        Vec3 moveLand = this.position().subtract(p_21372_.position());
        p_21372_.setDeltaMovement(moveLand.scale(0.1));
        if (this.level.isClientSide) {
            p_21372_.yOld = p_21372_.getY();
        }
        if(p_21372_ instanceof Player player){
            player.addEffect(new MobEffectInstance(MobEffects.DARKNESS,harmfulEffectDurationTime));
            player.addEffect(new MobEffectInstance(EffectsRegister.ENTANGLEMENT.get(),harmfulEffectDurationTime));
        }
        return p_21372_.hurt(DamageSource.MAGIC,(float) this.getAttributeValue(Attributes.ATTACK_DAMAGE));
    }

    public Silk shootSilk(LivingEntity target){
        Silk silk=new Silk(this.level,this);
        double d0 = target.getX() - this.getX();
        double d1 = target.getY(0.3333333333333333D) - silk.getY();
        double d2 = target.getZ() - this.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        silk.shoot(d0, d1 + d3 * (double)0.2F, d2, 1.6F, (float)(14 - this.level.getDifficulty().getId() * 4));
        this.level.addFreshEntity(silk);
        this.getContainer().getItem(0).setCount(0);
        return silk;
    }

    @Override
    public void tick() {
        if (clamDown >0 ) clamDown--;
        if (!this.isAttacking() && clamDown==20&&this.container.isEmpty()) container.addItem(new ItemStack(Items.ARROW));
        super.tick();
    }

    @Override
    protected void actuallyHurt(DamageSource p_21240_, float p_21241_) {
        super.actuallyHurt(p_21240_, p_21241_);
        this.setAttacking(false);
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public void setAttacking(boolean attacking) {
        isAttacking = attacking;
    }

    public SimpleContainer getContainer() {
        return container;
    }

    public void setContainer(SimpleContainer container) {
        this.container = container;
    }

    public int getSilkAttackDistanceMax() {
        return silkAttackDistanceMax;
    }

    public void setSilkAttackDistanceMax(int silkAttackDistanceMax) {
        this.silkAttackDistanceMax = silkAttackDistanceMax;
    }

    public int getSilkAttackDistanceMin() {
        return silkAttackDistanceMin;
    }

    public void setSilkAttackDistanceMin(int silkAttackDistanceMin) {
        this.silkAttackDistanceMin = silkAttackDistanceMin;
    }

    public int getTargetChangeTime() {
        return targetChangeTime;
    }

    public void setTargetChangeTime(int targetChangeTime) {
        this.targetChangeTime = targetChangeTime;
    }

    public int getRemainingPersistentAngerTime() {
        return remainingPersistentAngerTime;
    }

    public void setRemainingPersistentAngerTime(int remainingPersistentAngerTime) {
        this.remainingPersistentAngerTime = remainingPersistentAngerTime;
    }

    public int getClamDown() {
        return clamDown;
    }

    public void setClamDown(int clamDown) {
        this.clamDown = clamDown;
    }
}
