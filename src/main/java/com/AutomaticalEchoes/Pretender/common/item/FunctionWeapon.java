package com.AutomaticalEchoes.Pretender.common.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import org.apache.commons.lang3.function.TriFunction;

public class FunctionWeapon extends SwordItem {
    private TriFunction<ItemStack,LivingEntity,LivingEntity,Boolean> HurtEnemyFunction = (p_43278_, p_43279_, p_43280_) -> {
        p_43278_.hurtAndBreak(1, p_43280_, (p_43296_) -> {
            p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
    };
    public FunctionWeapon(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_  ) {
        super(p_43269_, p_43270_, p_43271_, p_43272_);

    }

    public FunctionWeapon  Function(TriFunction<ItemStack,LivingEntity,LivingEntity,Boolean> hurtEnemyFunction){
        this.HurtEnemyFunction = hurtEnemyFunction;
        return this;
    }

    @Override
    public boolean hurtEnemy(ItemStack p_43278_, LivingEntity p_43279_, LivingEntity p_43280_) {
        return HurtEnemyFunction.apply(p_43278_, p_43279_, p_43280_);
    }

    public static Boolean HalfLive(ItemStack p_43278_, LivingEntity p_43279_, LivingEntity p_43280_){
        float dam = p_43279_.getMaxHealth() - p_43279_.getHealth();
        p_43279_.hurt(DamageSource.indirectMagic(p_43279_,p_43280_),dam);
        p_43278_.hurtAndBreak( p_43278_.getMaxDamage(), p_43280_, (p_43296_) -> {
            p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
    }

    public static Boolean SoulCut(ItemStack p_43278_, LivingEntity p_43279_, LivingEntity p_43280_){
        if(!(p_43279_ instanceof Player) && p_43279_.level instanceof ServerLevel serverLevel){
            EntityType<?> type = p_43279_.getType();
            CompoundTag compoundTag = p_43279_.saveWithoutId(new CompoundTag());
            type.spawn(serverLevel,compoundTag,null,null,p_43279_.blockPosition(),MobSpawnType.MOB_SUMMONED,false,false);
            p_43278_.hurtAndBreak( p_43278_.getMaxDamage(), p_43280_, (p_43296_) -> {
                p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }

        return true;
    }
}
