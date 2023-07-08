package com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousSlime.Goal;

import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousSlime.SuspiciousSlime;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.function.Predicate;

public class LookingFoodGoal extends Goal {
    private final SuspiciousSlime slime;
    private Entity food;
    public LookingFoodGoal(SuspiciousSlime suspiciousSlime){
        this.slime = suspiciousSlime;
    }

    @Override
    public boolean canUse() {
        if(slime.level instanceof ServerLevel serverLevel &&  slime.isCarryItemEmpty()){
            List<Entity> entities = serverLevel.getEntities(slime, new AABB(slime.getX() - 10, slime.getY() - 3, slime.getZ() - 10, slime.getX() + 10, slime.getY() + 3, slime.getZ() + 10), new Predicate<Entity>() {
                @Override
                public boolean test(Entity entity) {
                    return entity instanceof ItemEntity;
                }
            });
            if(entities.isEmpty()) return false;
            this.food = entities.get(0);
            return true;
        }
        return false;
    }


    @Override
    public void start() {

    }

    @Override
    public void tick() {
        if (food != null && food instanceof ItemEntity itemEntity && food.isAlive()) {
            if(slime.distanceTo(food) < 1) {
                slime.pickUpItem(itemEntity);
                return;
            }
            this.slime.lookAt(food, 10.0F, 10.0F);
            ((IMoveControl)this.slime.getMoveControl()).setDirection(this.slime.getYRot(), true,false);
        }else {
            stop();
        }
    }
}
