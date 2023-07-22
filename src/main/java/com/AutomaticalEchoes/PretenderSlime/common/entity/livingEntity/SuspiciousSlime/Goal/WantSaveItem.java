package com.AutomaticalEchoes.PretenderSlime.common.entity.livingEntity.SuspiciousSlime.Goal;

import com.AutomaticalEchoes.PretenderSlime.api.Utils;
import com.AutomaticalEchoes.PretenderSlime.common.entity.livingEntity.SuspiciousSlime.SuspiciousSlime;
import com.AutomaticalEchoes.PretenderSlime.register.BlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;

public class WantSaveItem extends Goal {
    private final SuspiciousSlime slime;
    private BlockPos blockPos;
    public WantSaveItem(SuspiciousSlime suspiciousSlime){
        this.slime = suspiciousSlime;
    }

    @Override
    public boolean canUse() {
        if(Utils.isContainerFull(slime.getInventory())){
            if (slime.getBase() == null) {
                slime.locateSusSlimeBlock.start();
                return false;
            }
            this.blockPos = slime.getBase();
            return !slime.level.isClientSide && slime.level.getBlockState(blockPos).getBlock() == BlockRegister.SUSPICIOUS_SLIME_BLOCK.get();
        }
        return false;
    }

    @Override
    public void tick() {
        if(slime.blockPosition().distToCenterSqr(blockPos.getX(),blockPos.getY(),blockPos.getZ()) < 1) {

            slime.saveItemToBase();
            stop();
        }
        this.slime.lookAt(blockPos,10.0F,10.0F);
        ((IMoveControl)this.slime.getMoveControl()).setDirection(this.slime.getYRot(), true,false);
    }
}