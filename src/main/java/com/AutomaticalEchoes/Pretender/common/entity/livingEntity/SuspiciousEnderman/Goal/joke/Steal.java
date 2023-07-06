package com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousEnderman.Goal.joke;

import com.AutomaticalEchoes.Pretender.api.Joke;
import com.AutomaticalEchoes.Pretender.api.JokeCase;
import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousEnderman.SuspiciousEnderman;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class Steal extends Joke<Player> {
    public Steal(SuspiciousEnderman suspiciousEnderman) {
        super(suspiciousEnderman);
    }
    @Override
    public Case Case() {
        return Case.EMPTY;
    }

    @Override
    public boolean canJoke() {
        return super.canJoke();
    }

    @Override
    public void doJoke() {
        if(suspiciousEnderman.getJokingTarget() instanceof  Player target){
            Inventory playerInventory = target.getInventory();
            ItemStack selected = playerInventory.getSelected();
            if(selected != ItemStack.EMPTY){
                playerInventory.setItem(playerInventory.selected,ItemStack.EMPTY);
            }else {
                target.addEffect(new MobEffectInstance(MobEffects.DARKNESS,200));
            }
            suspiciousEnderman.setCarriedItem(selected);
        }
    }

    @Override
    public @Nullable Predicate<LivingEntity> TargetSelector() {
        return PLAYER;
    }
}
