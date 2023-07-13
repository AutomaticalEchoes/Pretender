package com.AutomaticalEchoes.Pretender.common.fluid.FluidTye;

import com.AutomaticalEchoes.Pretender.api.IFunction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class FunctionFluidType extends BaseFluidType {
    private @Nullable IFunction.QuadFunction<FluidState, LivingEntity, Vec3, Double,Boolean> MoveFunction;
    private @Nullable Function<ItemEntity,Boolean> ItemFunction;
    /**
     * Default constructor.
     *
     * @param properties the general properties of the fluid type
     */
    public FunctionFluidType(Properties properties, @Nullable IFunction.QuadFunction<FluidState, LivingEntity, Vec3, Double,Boolean> moveFunction ,@Nullable Function<ItemEntity,Boolean> itemFunction) {
        super(properties);
        this.MoveFunction = moveFunction;
        this.ItemFunction = itemFunction;
    }



    @Override
    public boolean move(FluidState state, LivingEntity entity, Vec3 movementVector, double gravity) {
        return MoveFunction !=null && MoveFunction.apply(state,entity,movementVector,gravity);
    }



    @Override
    public void setItemMovement(ItemEntity entity) {
        if(entity.tickCount % 20 == 0 && ItemFunction !=null) ItemFunction.apply(entity);
        super.setItemMovement(entity);
    }



}
