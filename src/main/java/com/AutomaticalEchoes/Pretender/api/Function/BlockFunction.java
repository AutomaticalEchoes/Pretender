package com.AutomaticalEchoes.Pretender.api.Function;

import com.AutomaticalEchoes.Pretender.Pretender;
import com.AutomaticalEchoes.Pretender.common.entity.blockEntity.SusSlimeBase;
import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousSlime.SuspiciousSlime;
import com.AutomaticalEchoes.Pretender.register.BlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;

import javax.annotation.Nullable;

public interface BlockFunction{
    static Boolean EmptyWithSlime(BlockState p_54760_, BlockGetter p_54761_, BlockPos p_54762_, CollisionContext p_54763_) {
        if (p_54763_ instanceof EntityCollisionContext collisionContext && collisionContext.getEntity() != null) {
            return !(collisionContext.getEntity() instanceof SuspiciousSlime);
        }
        return true;
    }

    @Nullable
    static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> p_152133_, BlockEntityType<E> p_152134_, BlockEntityTicker<? super E> p_152135_) {
        return p_152134_ == p_152133_ ? (BlockEntityTicker<A>)p_152135_ : null;
    }

    static void serverTick(Level p_155145_, BlockPos p_155146_, BlockState p_155147_, SusSlimeBase p_155148_) {
        if(p_155145_.getBlockTicks().count() % (30 * 1000) ==0){
            int i = 0;
            while (p_155148_.getContainer().getItem(i).isEmpty() && i < p_155148_.getContainer().getContainerSize()){
                i++;
            }
            if(i == p_155148_.getContainer().getContainerSize()) return;
            ItemStack itemStack = p_155148_.getContainer().removeItem(i, 1);
            ItemEntity itemEntity = new ItemEntity(p_155145_, p_155146_.getX() + 0.5, p_155146_.getY(), p_155146_.getZ() + 0.5, itemStack);
            p_155145_.addFreshEntity(itemEntity);
        }
    }

    static  <T extends BlockEntity> BlockEntityTicker SusSlimeTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return p_153212_.isClientSide ? null : createTickerHelper(p_153214_, BlockRegister.BlockEntityRegister.SUS_SLIME_BASE.get(), BlockFunction::serverTick);
    }

    static void onRemove(BlockState p_60515_, Level p_60516_, BlockPos p_60517_, BlockState p_60518_, boolean p_60519_) {
        if (p_60515_.hasBlockEntity() && (!p_60515_.is(p_60518_.getBlock()) || !p_60518_.hasBlockEntity())) {
            if(p_60516_.getBlockEntity(p_60517_) instanceof SusSlimeBase susSlimeBase){
                Containers.dropContents(p_60516_,p_60517_, susSlimeBase.getContainer());
            }
            p_60516_.removeBlockEntity(p_60517_);
        }

    }
}
