package com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousEnderman.Goal.joke;

import com.AutomaticalEchoes.Pretender.api.JokeCase;
import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousEnderman.SuspiciousEnderman;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class Steal implements JokeCase<Player> {
    private SuspiciousEnderman suspiciousEnderman;

    @Override
    public int Level() {
        return 0;
    }

    @Override
    public boolean canJoke() {
        return false;
    }

    @Override
    public void doJoke(Player target) {
        Inventory playerInventory = target.getInventory();
        ItemStack selected = playerInventory.getSelected();
        if(selected != ItemStack.EMPTY){
            playerInventory.setItem(playerInventory.selected,ItemStack.EMPTY);
        }else {
            target.addEffect(new MobEffectInstance(MobEffects.DARKNESS,200));
        }
        suspiciousEnderman.setCarriedItem(selected);
    }

    @Override
    public Player Target() {
        return null;
    }
}
