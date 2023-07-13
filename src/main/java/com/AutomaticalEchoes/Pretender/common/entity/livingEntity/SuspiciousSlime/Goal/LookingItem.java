package com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousSlime.Goal;

import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousSlime.SuspiciousSlime;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class LookingItem extends Goal {
    private final SuspiciousSlime slime;
    private @Nullable ItemEntity itemTarget;
    public LookingItem(SuspiciousSlime suspiciousSlime){
        this.slime = suspiciousSlime;
    }

    @Override
    public boolean canUse() {
        if(slime.level instanceof ServerLevel serverLevel){
            List<Entity> entities = serverLevel.getEntities(slime, new AABB(slime.getX() - 10, slime.getY() - 3, slime.getZ() - 10, slime.getX() + 10, slime.getY() + 3, slime.getZ() + 10), new Predicate<Entity>() {
                @Override
                public boolean test(Entity entity) {
                    return entity instanceof ItemEntity;
                }
            });
            if(entities.isEmpty()) return false;

            Optional<Entity> first = entities.stream().filter(entity -> {
                ItemEntity itemEntity = (ItemEntity) entity;
                return slime.getContainer().canAddItem(itemEntity.getItem());
            }).findFirst();


            if(first.isEmpty()) return false;

            this.itemTarget = (ItemEntity) first.get();
            return true ;
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        assert itemTarget != null;
        return itemTarget.isAlive() && !slime.hasLineOfSight(itemTarget);
    }

    @Override
    public void start() {

    }

    @Override
    public void tick() {
        if (itemTarget != null && itemTarget.isAlive()) {
            if(slime.distanceTo(itemTarget) < 0.5) {
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
