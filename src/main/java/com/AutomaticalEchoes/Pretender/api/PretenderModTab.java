package com.AutomaticalEchoes.Pretender.api;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class PretenderModTab extends CreativeModeTab {
    public PretenderModTab(String label) {
        super(label);
    }

    @Override
    public ItemStack makeIcon() {
        return  new ItemStack(Items.CREEPER_BANNER_PATTERN);
    }
}
