package com.AutomaticalEchoes.Pretender.register;

import com.AutomaticalEchoes.Pretender.Pretender;
import com.AutomaticalEchoes.Pretender.common.item.SuspiciousEnderPearlItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemsRegister {
    private static Item.Properties CreateProperties(){
        return new Item.Properties().tab(Pretender.MY_TAB) ;
    }
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, Pretender.MOD_ID);

    public static final RegistryObject<Item> SUSPICIOUS_ENDERMAN =REGISTRY.register("suspicious_enderman_spawn_egg",() ->new ForgeSpawnEggItem(EntityRegister.SUSPICIOUS_ENDERMAN,1447446, 0,CreateProperties()));
    public static final RegistryObject<Item> SUSPICIOUS_THROWN_ENDERPEARL_ITEM =REGISTRY. register("suspicious_thrown_enderpearl",() -> new SuspiciousEnderPearlItem(CreateProperties()));
}
