package com.AutomaticalEchoes.PretenderSlime.common.entity.livingEntity.SuspiciousSlime.Goal;

import com.AutomaticalEchoes.PretenderSlime.api.Utils;
import com.AutomaticalEchoes.PretenderSlime.common.entity.livingEntity.SuspiciousSlime.SuspiciousSlime;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class LocateItem extends Goal {
    private final SuspiciousSlime slime;
    private @Nullable ItemEntity itemTarget;
    public LocateItem(SuspiciousSlime suspiciousSlime){
        this.slime = suspiciousSlime;
    }

    @Override
    public boolean canUse() {
        if(slime.level instanceof ServerLevel serverLevel && slime.wantCollectItem()){
            List<Entity> entities = serverLevel.getEntities(slime, new AABB(slime.getX() - 10, slime.getY() - 3, slime.getZ() - 10, slime.getX() + 10, slime.getY() + 3, slime.getZ() + 10), new Predicate<Entity>() {
                @Override
                public boolean test(Entity entity) {
                    if(entity instanceof ItemEntity itemEntity){
                        return slime.getInventory().canAddItem(itemEntity.getItem());
                    }
                    return false;
                }
            });
            if(entities.isEmpty()) return false;
            this.itemTarget = (ItemEntity) Utils.getNearestEntity(entities,slime);
            return true ;
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        assert itemTarget != null;
        return itemTarget.isAlive() && slime.hasLineOfSight(itemTarget);
    }

    @Override
    public void start() {

    }

    @Override
    public void tick() {
        if (itemTarget != null && itemTarget.isAlive()) {
            if(slime.distanceTo(itemTarget) < 0.5 * slime.getSize()) {
                slime.tryPickUp(itemTarget);
                return;
            }
            this.slime.lookAt(itemTarget, 10.0F, 10.0F);
            ((IMoveControl)this.slime.getMoveControl()).setDirection(this.slime.getYRot(), true,false);
        }else {
            stop();
        }
    }
}
