package com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousEnderman.Goal.joke;

import com.AutomaticalEchoes.Pretender.api.Joke;
import com.AutomaticalEchoes.Pretender.api.JokeCase;
import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousEnderman.SuspiciousEnderman;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class FrameUp extends Joke<Player> {
    public FrameUp(SuspiciousEnderman suspiciousEnderman) {
        super(suspiciousEnderman);
    }

    @Override
    public Case Case() {
        return Case.CARRIED;
    }

    @Override
    public boolean canJoke() {
        return super.canJoke();
    }

    @Override
    public void doJoke() {
        if(suspiciousEnderman.getJokingTarget() instanceof Player target){
            Inventory playerInventory = target.getInventory();
            ItemStack itemStack = suspiciousEnderman.getCarriedItem();
            int freeSlot = playerInventory.getFreeSlot();
            int slotWithRemainingSpace = playerInventory.getSlotWithRemainingSpace(itemStack);
            int slotNum = freeSlot == -1? slotWithRemainingSpace : freeSlot ;
            if(slotNum != -1){
                playerInventory.setItem(slotNum,itemStack);
                suspiciousEnderman.setCarriedItem(ItemStack.EMPTY);
            }
        }

    }

    @Override
    public @Nullable Predicate<LivingEntity> TargetSelector() {
        return PLAYER;
    }
}
