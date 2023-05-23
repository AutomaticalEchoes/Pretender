package com.AutomaticalEchoes.Pretender.common.fluid.FluidTye;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidType;

public class NonNewtonianFluidType extends BaseFluidType {
    private float friction = 0.8F;
    /**
     * Default constructor.
     *
     * @param properties the general properties of the fluid type
     */
    public NonNewtonianFluidType(Properties properties) {
        super(properties);
    }

    public NonNewtonianFluidType(Properties properties,float factor){
        super(properties);
        this.friction =  factor > 0.6? factor : 0.6F;
    }



    @Override
    public boolean move(FluidState state, LivingEntity entity, Vec3 movementVector, double gravity) {
        entity.setSprinting(false);
        boolean flag = entity.getDeltaMovement().y <= 0.0D;
        double d9 = entity.getY();
        float f1 = entity.isSprinting() ? friction + 0.1F : friction;
        float f2 = (float) ((1 - f1) * 10E-5);
        float f3 = (float) (movementVector.length() + f2 * 10) ;
        float f4 =  f2 / f3;
        float f5 = 0.02F;
        f5 *= (float)entity.getAttribute(net.minecraftforge.common.ForgeMod.SWIM_SPEED.get()).getValue();
        entity.moveRelative(f5, movementVector);
        entity.move(MoverType.SELF, entity.getDeltaMovement());
        Vec3 vec36 = entity.getDeltaMovement();
        if (entity.horizontalCollision && entity.onClimbable()) {
            vec36 = new Vec3(vec36.x, 0.2D, vec36.z);
        }
        entity.setDeltaMovement(vec36.multiply(f4,f4,f4));
        Vec3 vec32 = entity.getFluidFallingAdjustedMovement(gravity, flag, entity.getDeltaMovement());
        entity.setDeltaMovement(vec32);
        if (entity.horizontalCollision && entity.isFree(vec32.x, vec32.y + (double)0.6F - entity.getY() + d9, vec32.z)){
            entity.setDeltaMovement(vec32.x, (double)0.3F, vec32.z);
        }
        return true;
    }
}
