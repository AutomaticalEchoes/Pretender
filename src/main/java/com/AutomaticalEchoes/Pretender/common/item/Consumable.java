package com.AutomaticalEchoes.Pretender.common.item;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.function.TriFunction;

public class Consumable extends Item {
    private TriFunction<Level,Player,InteractionHand,InteractionResultHolder<ItemStack>> UseFunction  = (p_41432_, p_41433_,p_41434_)  ->{
        ItemStack itemstack = p_41433_.getItemInHand(p_41434_);
        if (itemstack.isEdible()) {
            if (p_41433_.canEat(itemstack.getFoodProperties(p_41433_).canAlwaysEat())) {
                p_41433_.startUsingItem(p_41434_);
                return InteractionResultHolder.consume(itemstack);
            } else {
                return InteractionResultHolder.fail(itemstack);
            }
        } else {
            return InteractionResultHolder.pass(p_41433_.getItemInHand(p_41434_));
        }
    };

    public Consumable(Properties p_41383_) {
        super(p_41383_);
    }

    public Consumable UseFunction(TriFunction<Level,Player,InteractionHand,InteractionResultHolder<ItemStack>> useFunction){
        this.UseFunction = useFunction;
        return this;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
        InteractionResultHolder<ItemStack> resultHolder = UseFunction.apply(p_41432_, p_41433_, p_41434_);
        if(resultHolder.getResult() == InteractionResult.CONSUME){
            ItemStack itemInHand = p_41433_.getItemInHand(p_41434_);
            itemInHand.shrink(1);
            p_41433_.getCooldowns().addCooldown(this, 1200);
            if(p_41432_ instanceof ClientLevel clientLevel ){
                clientLevel.playSound(p_41433_,p_41433_, SoundEvents.GLASS_BREAK, SoundSource.VOICE,1.0F,1.0F);
            }
        }
        return resultHolder;
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 4;
    }


//    public static InteractionResultHolder<ItemStack> BoneShield(Level p_41432_, Player p_41433_, InteractionHand p_41434_){
//        ItemStack itemInHand = p_41433_.getItemInHand(p_41434_);
//        if(p_41432_ instanceof ServerLevel serverLevel){
//            p_41433_.addEffect(new MobEffectInstance(EffectsRegister.BONE_SHIELD.get(), 1200,0));
//        }
//        return InteractionResultHolder.consume(itemInHand);
//    }

}
