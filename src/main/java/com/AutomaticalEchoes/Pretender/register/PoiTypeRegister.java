package com.AutomaticalEchoes.Pretender.register;

import com.AutomaticalEchoes.Pretender.Pretender;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class PoiTypeRegister {
    public static final DeferredRegister<PoiType> DEFERRED_REGISTER =DeferredRegister.create(ForgeRegistries.POI_TYPES , Pretender.MOD_ID);
    public static final RegistryObject<PoiType> SUS_SLIME_BLOCK = DEFERRED_REGISTER.register("sus_slime_block",() ->new PoiType(getBlockStates(BlockRegister.SUSPICIOUS_SLIME_BLOCK.get()),1,1));
    private static Set<BlockState> getBlockStates(Block p_218074_) {
        return ImmutableSet.copyOf(p_218074_.getStateDefinition().getPossibleStates());
    }




}
