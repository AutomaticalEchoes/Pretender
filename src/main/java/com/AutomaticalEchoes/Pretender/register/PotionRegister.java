package com.AutomaticalEchoes.Pretender.register;


import com.AutomaticalEchoes.Pretender.PretenderSlime;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PotionRegister {
    public static final DeferredRegister<Potion> REGISTRY = DeferredRegister.create(ForgeRegistries.POTIONS, PretenderSlime.MOD_ID);
    public static final RegistryObject<Potion> SUS_WATER = REGISTRY.register("sus_water", Potion::new);
    public static final RegistryObject<Potion> MUCUS = REGISTRY.register("mucus",Potion::new);
    public static final RegistryObject<Potion> ACIDITY = REGISTRY.register("acidity", Potion::new);
    public static final RegistryObject<Potion> INVALID_ARMOR = REGISTRY.register("invalid_armor_potion",() -> new Potion("invalid_armor",new MobEffectInstance(EffectsRegister.INVALID_ARMOR.get(),4800)));
    public static final RegistryObject<Potion> ACID_EROSION = REGISTRY.register("acid_erosion_potion",()-> new Potion("acid_erosion",new MobEffectInstance(EffectsRegister.ACID_EROSION.get(),4800)));

}
