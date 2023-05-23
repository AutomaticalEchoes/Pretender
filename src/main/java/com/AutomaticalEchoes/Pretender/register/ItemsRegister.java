package com.AutomaticalEchoes.Pretender.register;

import com.AutomaticalEchoes.Pretender.Pretender;
import com.AutomaticalEchoes.Pretender.common.item.SuspiciousEnderPearlItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemsRegister {
    private static Item.Properties CreateProperties(){
        return new Item.Properties().tab(Pretender.MY_TAB) ;
    }
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, Pretender.MOD_ID);

    public static final RegistryObject<Item> SUSPICIOUS_CREEPER_SAC =REGISTRY.register("suspicious_creeper_sac",() -> new Item(CreateProperties()));
    public static final RegistryObject<Item> SUSPICIOUS_SLIME_BALL = REGISTRY.register("suspicious_slime_ball",() -> new Item(CreateProperties()));

    public static final RegistryObject<Item> SUSPICIOUS_CREEPER =REGISTRY.register("suspicious_creeper_spawn_egg",() -> new ForgeSpawnEggItem(EntityRegister.SUSPICIOUS_CREEPER,894731, 0,CreateProperties()));
    public static final RegistryObject<Item> SUSPICIOUS_ENDERMAN =REGISTRY.register("suspicious_enderman_spawn_egg",() ->new ForgeSpawnEggItem(EntityRegister.SUSPICIOUS_ENDERMAN,1447446, 0,CreateProperties()));
    public static final RegistryObject<Item> SUSPICIOUS_SLIME =REGISTRY.register("suspicious_slime_spawn_egg",() ->new ForgeSpawnEggItem(EntityRegister.SUSPICIOUS_SLIME,5349438, 8306542,CreateProperties()));
    public static final RegistryObject<Item> SUSPICIOUS_SPIDER = REGISTRY.register("suspicious_spider_spawn_egg",() -> new ForgeSpawnEggItem(EntityRegister.SUSPICIOUS_SPIDER,3419431, 11013646,CreateProperties()));
    public static final RegistryObject<Item> SUSPICIOUS_SKELETON = REGISTRY.register("suspicious_skeleton_spawn_egg",() -> new ForgeSpawnEggItem(EntityRegister.SUSPICIOUS_SKELETON,12698049, 4802889,CreateProperties()));

    public static final RegistryObject<Item> MUCUS_BUCKET = REGISTRY.register("mucus_bucket",() -> new BucketItem(FluidRegister.MUCUS, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1).tab(Pretender.MY_TAB)));
    public static final RegistryObject<Item> ACIDITY_BUCKET = REGISTRY.register("acidity_bucket",() -> new BucketItem(FluidRegister.ACIDITY, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1).tab(Pretender.MY_TAB)));
    public static final RegistryObject<Item> SUSPICIOUS_WATER_BUCKET = REGISTRY.register("suspicious_water_bucket",()-> new BucketItem(FluidRegister.SUSPICIOUS_WATER,(new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1).tab(Pretender.MY_TAB)));
    public static final RegistryObject<Item> SUSPICIOUS_THROWN_ENDERPEARL_ITEM =REGISTRY. register("suspicious_thrown_enderpearl",() -> new SuspiciousEnderPearlItem(CreateProperties()));
}
