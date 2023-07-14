package com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousSlime.Goal;

import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousSlime.SuspiciousSlime;
import com.AutomaticalEchoes.Pretender.register.BlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;

public class LocateSusSlimeBlock extends Goal {
    private final SuspiciousSlime slime;
    public LocateSusSlimeBlock(SuspiciousSlime suspiciousSlime){
        this.slime = suspiciousSlime;
    }

    @Override
    public boolean canUse() {
        return !slime.level.isClientSide && (slime.getBase() == null || slime.level.getBlockState(slime.getBase()).getBlock() != BlockRegister.SUSPICIOUS_SLIME_BLOCK.get());
    }

    @Override
    public void start() {
        List<BlockPos> blockPos = slime.locateSusSlimeBlock();
        slime.setBase(blockPos.isEmpty()? null :blockPos.get(0));
    }
}
