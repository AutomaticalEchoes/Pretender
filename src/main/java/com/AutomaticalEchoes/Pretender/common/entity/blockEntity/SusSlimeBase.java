package com.AutomaticalEchoes.Pretender.common.entity.blockEntity;

import com.AutomaticalEchoes.Pretender.Pretender;
import com.AutomaticalEchoes.Pretender.register.BlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class SusSlimeBase extends BlockEntity {
    private final int size = 16;
    private final SimpleContainer simpleContainer = new SimpleContainer(16);
    public static SusSlimeBase Create(BlockPos pos,BlockState state){
        return new SusSlimeBase(BlockRegister.BlockEntityRegister.SUS_SLIME_BASE.get(),pos,state);
    }

    public SusSlimeBase(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }

    public void load(CompoundTag p_155349_) {
        super.load(p_155349_);
        this.simpleContainer.fromTag(p_155349_.getList("container",10));
        Pretender.LOGGER.info("show item:" + p_155349_);
    }

    protected void saveAdditional(CompoundTag p_187489_) {
        super.saveAdditional(p_187489_);
        p_187489_.put("container", this.simpleContainer.createTag());
        Pretender.LOGGER.info("save item:" + p_187489_);
    }



    public SimpleContainer getContainer() {
        return simpleContainer;
    }
}
