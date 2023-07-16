package com.AutomaticalEchoes.Pretender.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModCommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER=new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;


    public static final ForgeConfigSpec.ConfigValue<Integer> SUSPICIOUS_SLIME_ACIDITY_AREA_EFFECT_RADIUS;
    public static final ForgeConfigSpec.ConfigValue<Integer> SUSPICIOUS_SLIME_ACIDITY_AREA_EFFECT_DURATION_TIME;
    public static final ForgeConfigSpec.ConfigValue<Integer> SUSPICIOUS_SLIME_ACIDITY_DISTANCE;
    public static final ForgeConfigSpec.ConfigValue<Integer> SUSPICIOUS_SLIME_WANT_COLLECT_TICK;
    public static final ForgeConfigSpec.ConfigValue<Integer> SUSPICIOUS_SLIME_TRANSLATE_TICK;
    public static final ForgeConfigSpec.ConfigValue<Integer> SUSPICIOUS_SLIME_BASE_CREATE_SLIME_TICK;


    static {
        BUILDER.push("myMod config");

        BUILDER.push("SUSPICIOUS_SLIME");
        SUSPICIOUS_SLIME_ACIDITY_AREA_EFFECT_RADIUS=BUILDER.defineInRange("SUSPICIOUS_SLIME acidity area effect radius",5,3,7);
        SUSPICIOUS_SLIME_ACIDITY_AREA_EFFECT_DURATION_TIME=BUILDER.defineInRange("SUSPICIOUS_SLIME acidity area effect duration time",20,15,30);
        SUSPICIOUS_SLIME_ACIDITY_DISTANCE=BUILDER.defineInRange("SUSPICIOUS_SLIME acidity distance",5,5,11);
        SUSPICIOUS_SLIME_WANT_COLLECT_TICK=BUILDER.defineInRange("SUSPICIOUS_SLIME acidity prepare tick",4,3,5);
        SUSPICIOUS_SLIME_TRANSLATE_TICK=BUILDER.defineInRange("SUSPICIOUS_SLIME translate tick",30,15,45);
        SUSPICIOUS_SLIME_BASE_CREATE_SLIME_TICK = BUILDER.defineInRange("SUSPICIOUS_SLIME_BASE create slime tick",450,300,600);
        BUILDER.pop();

        BUILDER.pop();
        SPEC=BUILDER.build();
    }
}
