package com.AutomaticalEchoes.Pretender.register;

import com.AutomaticalEchoes.Pretender.Pretender;
import com.AutomaticalEchoes.Pretender.common.effect.AcidErosion;
import com.AutomaticalEchoes.Pretender.common.effect.BaseEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EffectsRegister {
    public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Pretender.MOD_ID);
    public static final RegistryObject<MobEffect> ENTANGLEMENT = REGISTRY.register("entanglement",
            () -> BaseEffect.Create(MobEffectCategory.HARMFUL,5926782)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED,"4C708E88-8702-455F-8A0D-8AB3131070BD",(double) -1.0F, AttributeModifier.Operation.MULTIPLY_TOTAL));
    public static final RegistryObject<MobEffect> INVALID_ARMOR=REGISTRY.register("invalid_armor",
            () -> BaseEffect.Create(MobEffectCategory.HARMFUL,5865782)
                    .addAttributeModifier(Attributes.ARMOR,"CB65EE4B-E64E-4BA1-824D-17F328D2E10C",(double) -0.4F, AttributeModifier.Operation.MULTIPLY_TOTAL)
                    .addAttributeModifier(Attributes.ARMOR_TOUGHNESS,"0A96DD88-9109-4533-98D2-450D4B622BD6",(double) -0.4F, AttributeModifier.Operation.MULTIPLY_TOTAL));
    public static final RegistryObject<AcidErosion> ACID_EROSION =REGISTRY.register("acid_erosion",
            () -> new AcidErosion(MobEffectCategory.HARMFUL, 16296963));

}
