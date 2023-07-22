package com.AutomaticalEchoes.PretenderSlime.api;

import com.AutomaticalEchoes.PretenderSlime.common.block.NonNewtonianFluidBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;

public class Utils {
    public static float RadiusWithSpeed(double dealtMovementL){
        return (float) Math.floor(dealtMovementL * 3);
    }

    public static BlockPos RandomVecWithRange(LivingEntity livingEntity,double Range){
        return RandomVecWithRange(livingEntity.getRandomX(Range),255,livingEntity.getRandomZ(Range),livingEntity.level);
    }

    public static BlockPos RandomVecWithRange(double p_32544_, double p_32545_, double p_32546_ , Level level){
    BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(p_32544_, p_32545_, p_32546_);

        while(blockpos$mutableblockpos.getY() > level.getMinBuildHeight() && !level.getBlockState(blockpos$mutableblockpos).getMaterial().blocksMotion()) {
        blockpos$mutableblockpos.move(Direction.DOWN);
    }

    BlockState blockstate = level.getBlockState(blockpos$mutableblockpos);
    boolean flag = blockstate.getMaterial().blocksMotion();
    if(flag) return blockpos$mutableblockpos;
    return BlockPos.ZERO;
    }


    public static boolean isContainerFull(Container container){
        boolean hasEmpty = container.hasAnyMatching(itemStack -> itemStack.isEmpty() || itemStack.getCount() < itemStack.getMaxStackSize());
        return !hasEmpty;
    }


    @Nullable
    public static  <T extends Entity> T getNearestEntity(List<? extends T> p_45983_, Entity entity){
        return getNearestEntity(p_45983_,entity.getX(),entity.getY(),entity.getZ());
    }

    @Nullable
    public static  <T extends Entity> T getNearestEntity(List<? extends T> p_45983_, double p_45986_, double p_45987_, double p_45988_) {
        double d0 = -1.0D;
        T t = null;

        for(T t1 : p_45983_) {
            double d1 = t1.distanceToSqr(p_45986_, p_45987_, p_45988_);
            if (d0 == -1.0D || d1 < d0) {
                d0 = d1;
                t = t1;
            }
        }

        return t;
    }

    public static boolean inside(VoxelShape p_82886_, BlockPos p_82887_,Entity entity){
        return entity.getBlockStateOn().getBlock() instanceof NonNewtonianFluidBlock;
    }

    public static int IColor(int k){
        float f = 0.0F;
        float f1 = 0.0F;
        float f2 = 0.0F;
        int j = 1;
        f += (float)(k >> 16 & 255) / 255.0F;
        f1 += (float) (k >> 8 & 255) / 255.0F;
        f2 += (float)(k >> 0 & 255) / 255.0F;
        f = f / (float)j * 255.0F;
        f1 = f1 / (float)j * 255.0F;
        f2 = f2 / (float)j * 255.0F;
        return (int)f << 16 | (int)f1 << 8 | (int)f2;
    }

}
