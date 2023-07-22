package com.AutomaticalEchoes.PretenderSlime.register;

import com.AutomaticalEchoes.PretenderSlime.PretenderSlime;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemsRegister {
    private static Item.Properties CreateProperties(){
        return new Item.Properties().tab(PretenderSlime.TAB) ;
    }
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, PretenderSlime.MOD_ID);
    public static final RegistryObject<Item> SUSPICIOUS_SLIME_BALL = REGISTRY.register("suspicious_slime_ball",() -> new Item(CreateProperties()));
    public static final RegistryObject<Item> SUSPICIOUS_SLIME = REGISTRY.register("suspicious_slime_spawn_egg",() ->new ForgeSpawnEggItem(EntityRegister.SUSPICIOUS_SLIME,5349438, 8306542,CreateProperties()));
    public static final RegistryObject<Item> MUCUS_BUCKET = REGISTRY.register("mucus_bucket",() -> new BucketItem(FluidRegister.MUCUS, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1).tab(PretenderSlime.TAB)));
    public static final RegistryObject<Item> ACIDITY_BUCKET = REGISTRY.register("acidity_bucket",() -> new BucketItem(FluidRegister.ACIDITY, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1).tab(PretenderSlime.TAB)));
    public static final RegistryObject<Item> SUSPICIOUS_WATER_BUCKET = REGISTRY.register("suspicious_water_bucket",()-> new BucketItem(FluidRegister.SUSPICIOUS_WATER,(new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1).tab(PretenderSlime.TAB)));
}
