package com.AutomaticalEchoes.PretenderSlime.common.entity.livingEntity.SuspiciousSlime.Goal;

import com.AutomaticalEchoes.PretenderSlime.common.entity.livingEntity.SuspiciousSlime.SuspiciousSlime;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class KeepJumpingGoal extends Goal {
    private final SuspiciousSlime slime;

    public KeepJumpingGoal(SuspiciousSlime p_33660_) {
        this.slime = p_33660_;
        this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
    }
    public boolean canUse() {
        return !this.slime.isPassenger();
    }
    public void tick() {
        ((IMoveControl)this.slime.getMoveControl()).setWantedMovement(1.0D);
    }
}
