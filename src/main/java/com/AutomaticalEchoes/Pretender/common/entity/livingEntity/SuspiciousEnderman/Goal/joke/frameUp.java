package com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousEnderman.Goal.joke;

import com.AutomaticalEchoes.Pretender.api.JokeCase;
import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousEnderman.SuspiciousEnderman;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class frameUp implements JokeCase<Player> {
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
        ItemStack itemStack = suspiciousEnderman.getCarriedItem();
        int freeSlot = playerInventory.getFreeSlot();
        int slotWithRemainingSpace = playerInventory.getSlotWithRemainingSpace(itemStack);
        int slotNum = freeSlot == -1? slotWithRemainingSpace : freeSlot ;
        if(slotNum != -1){
            playerInventory.setItem(slotNum,itemStack);
            suspiciousEnderman.setCarriedItem(ItemStack.EMPTY);
        }
    }

    @Override
    public Player Target() {
        return null;
    }
}
