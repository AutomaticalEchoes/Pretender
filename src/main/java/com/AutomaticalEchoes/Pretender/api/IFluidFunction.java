package com.AutomaticalEchoes.Pretender.api;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TieredItem;

public interface IFluidFunction {

    static boolean HurtArmor(LivingEntity livingEntity){
        int[] all = {0, 1, 2, 3};
        int[] part ={0,1};
        if(livingEntity instanceof Player player){
            player.getInventory().hurtArmor(DamageSource.GENERIC,4,player.isSwimming() && player.isSprinting()?  all: part);
            return true;
        }
        return false;
    }

    static boolean Transform(ItemEntity itemEntity){
        Item item = itemEntity.getItem().getItem();
        if(itemEntity.getAge() > 600 && (item instanceof ArmorItem || item instanceof TieredItem)){
            int i1 = itemEntity.getItem().getMaxDamage() - itemEntity.getItem().getDamageValue();
            int num = i1 / 100;
            int exp = i1 % 100;
            for (int i = 0; i < num; i++) {
                ExperienceOrb experienceOrb = new ExperienceOrb(itemEntity.level, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), 100);
                itemEntity.level.addFreshEntity(experienceOrb);
            }
            ExperienceOrb experienceOrb = new ExperienceOrb(itemEntity.level, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), exp);
            itemEntity.level.addFreshEntity(experienceOrb);
            itemEntity.discard();
            return true;
        }
        return false;
    }

}
