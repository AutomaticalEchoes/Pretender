package com.AutomaticalEchoes.Pretender.common.fluid.FluidTye;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class FunctionFluidType extends BaseFluidType {
    private @Nullable Function<LivingEntity,Boolean> MoveFunction;
    private @Nullable Function<ItemEntity,Boolean> ItemFunction;
    /**
     * Default constructor.
     *
     * @param properties the general properties of the fluid type
     */
    public FunctionFluidType(Properties properties, @Nullable Function<LivingEntity,Boolean> moveFunction ,@Nullable Function<ItemEntity,Boolean> itemFunction) {
        super(properties);
        this.MoveFunction = moveFunction;
        this.ItemFunction = itemFunction;
    }



    @Override
    public boolean move(FluidState state, LivingEntity entity, Vec3 movementVector, double gravity) {
       if(entity.tickCount % 20 == 0 && MoveFunction !=null)  MoveFunction.apply(entity);
        return super.move(state, entity, movementVector, gravity);
    }



    @Override
    public void setItemMovement(ItemEntity entity) {
        if(entity.tickCount % 20 == 0 && ItemFunction !=null) ItemFunction.apply(entity);
        super.setItemMovement(entity);
    }



}
