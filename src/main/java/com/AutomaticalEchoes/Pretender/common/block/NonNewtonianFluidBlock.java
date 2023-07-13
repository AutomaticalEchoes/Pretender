package com.AutomaticalEchoes.Pretender.common.block;

import com.AutomaticalEchoes.Pretender.api.QuadFunction;
import com.AutomaticalEchoes.Pretender.register.ItemsRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Supplier;

public class NonNewtonianFluidBlock extends HalfTransparentBlock implements BucketPickup {
    private Supplier<? extends  Fluid> fluidSupplier = ForgeRegistries.FLUIDS.getDelegateOrThrow(Fluids.WATER);
    private Supplier<? extends Item> bucketPickupItem = ItemsRegister.SUSPICIOUS_WATER_BUCKET;
    private QuadFunction<BlockState, BlockGetter, BlockPos, CollisionContext,Boolean> CustomCollisionShape = (state, blockGetter, pos, collisionContext) -> false;
    private double YieldingStress = 1;
    public NonNewtonianFluidBlock(Properties p_49795_) {
        super(p_49795_);
    }

    public NonNewtonianFluidBlock CustomCustomCollisionShape(QuadFunction<BlockState, BlockGetter, BlockPos, CollisionContext,Boolean> customCollisionShape){
        this.CustomCollisionShape = customCollisionShape;
        return this;
    }

    public NonNewtonianFluidBlock YieldingStress(Double yieldingStress){
        this.YieldingStress = yieldingStress;
        return this;
    }

    public NonNewtonianFluidBlock Fluid(Supplier<? extends Fluid> iFluid){
        this.fluidSupplier = iFluid;
        return this;
    }

    public NonNewtonianFluidBlock BucketPickupItem(Supplier<? extends Item> Item){
        this.bucketPickupItem = Item;
        return this;
    }


    public VoxelShape getCollisionShape(BlockState p_54760_, BlockGetter p_54761_, BlockPos p_54762_, CollisionContext p_54763_) {
        boolean flag = true;
        boolean flag1 = shapeRule(p_54760_, p_54761_, p_54762_, p_54763_);
        Boolean flag2 = CustomCollisionShape.apply(p_54760_, p_54761_, p_54762_, p_54763_);
        flag = flag1 ? flag2 : false;
        return  flag ? Shapes.block() : Shapes.empty();

    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return Shapes.empty();
    }


    @Override
    public float getFriction(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        return entity != null && entity.getDeltaMovement().length() > YieldingStress?  0.6F : 0.8F;
    }

    public void fallOn(Level p_154567_, BlockState p_154568_, BlockPos p_154569_, Entity p_154570_, float p_154571_) {
        if (p_154570_.isSuppressingBounce() || Math.abs(p_154570_.getDeltaMovement().y) > YieldingStress) {
            super.fallOn(p_154567_, p_154568_, p_154569_, p_154570_, p_154571_);
        } else {
            p_154570_.causeFallDamage(p_154571_, 0.0F, DamageSource.FALL);
        }
    }

    public void updateEntityAfterFallOn(BlockGetter p_56406_, Entity p_56407_) {
        if (p_56407_.isSuppressingBounce() || Math.abs(p_56407_.getDeltaMovement().y) > YieldingStress) {
            super.updateEntityAfterFallOn(p_56406_, p_56407_);
        } else {
            this.bounceUp(p_56407_);
        }
    }

    @Override
    public FluidState getFluidState(BlockState p_60577_) {
        return fluidSupplier.get().defaultFluidState();
    }

    private void bounceUp(Entity p_56404_) {
        Vec3 vec3 = p_56404_.getDeltaMovement();
        if (vec3.y < 0.0D) {
            double d0 = p_56404_ instanceof LivingEntity ? 1.0D : 0.8D;
            p_56404_.setDeltaMovement(vec3.x, -vec3.y * d0, vec3.z);
        }

    }

    @Override
    public ItemStack pickupBlock(LevelAccessor p_152719_, BlockPos p_152720_, BlockState p_152721_) {
        p_152719_.setBlock(p_152720_, Blocks.AIR.defaultBlockState(),11);
        return bucketPickupItem.get().getDefaultInstance();
    }

    @Override
    public Optional<SoundEvent> getPickupSound() {
        return Optional.empty();
    }

    private boolean shapeRule(BlockState p_54760_, BlockGetter p_54761_, BlockPos p_54762_, CollisionContext p_54763_){
        if(p_54763_ instanceof EntityCollisionContext collisionContext && collisionContext.getEntity() !=null){
            Vec3 deltaMovement = collisionContext.getEntity().getDeltaMovement();
            if(deltaMovement.length() > 0.1 * YieldingStress){
                return true;
            }
            if(deltaMovement.y > -0.1 * YieldingStress && Math.abs(deltaMovement.y) < 0.06) {
                return false;
            }
        }
        return false;
    }
}
