package com.AutomaticalEchoes.Pretender.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModCommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER=new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.ConfigValue<Integer> RAGE_TARGET_EFFECT_DURATION_TICK;
    public static final ForgeConfigSpec.ConfigValue<Integer> RAGE_TARGET_EFFECT_SPEEDUP_LEVEL;

    public static final ForgeConfigSpec.ConfigValue<Integer> SUSPICIOUS_CREEPER_RAGE_EFFECT_DURATION_TIME;
    public static final ForgeConfigSpec.ConfigValue<Integer> SUSPICIOUS_CREEPER_INJECT_TIME;
    public static final ForgeConfigSpec.ConfigValue<Integer> SUSPICIOUS_CREEPER_EXPLODE_RADIUS;
    public static final ForgeConfigSpec.ConfigValue<Integer> SUSPICIOUS_CREEPER_AREA_EFFECT_RADIUS;
    public static final ForgeConfigSpec.ConfigValue<Integer> SUSPICIOUS_CREEPER_AREA_EFFECT_DURATION_TIME;

    public static final ForgeConfigSpec.ConfigValue<Integer> SUSPICIOUS_ENDERMAN_AREA_EFFECT_RADIUS;
    public static final ForgeConfigSpec.ConfigValue<Integer> SUSPICIOUS_ENDERMAN_AREA_EFFECT_DURATION_TIME;
    public static final ForgeConfigSpec.ConfigValue<Integer> SUSPICIOUS_ENDERMAN_HARMFUL_EFFECT_DURATION_TIME;
    public static final ForgeConfigSpec.ConfigValue<Integer> SUSPICIOUS_ENDERMAN_HURT_TICK;
    public static final ForgeConfigSpec.ConfigValue<Integer> SUSPICIOUS_ENDERMAN_PREPARE_TICK;

    public static final ForgeConfigSpec.ConfigValue<Integer> SUSPICIOUS_SLIME_ACIDITY_AREA_EFFECT_RADIUS;
    public static final ForgeConfigSpec.ConfigValue<Integer> SUSPICIOUS_SLIME_ACIDITY_AREA_EFFECT_DURATION_TIME;
    public static final ForgeConfigSpec.ConfigValue<Integer> SUSPICIOUS_SLIME_ACIDITY_DISTANCE;
    public static final ForgeConfigSpec.ConfigValue<Integer> SUSPICIOUS_SLIME_PREPARE_TICK;
    public static final ForgeConfigSpec.ConfigValue<Integer> SUSPICIOUS_SLIME_TRANSLATE_TICK;

    public static final ForgeConfigSpec.ConfigValue<Integer> SUSPICIOUS_SPIDER_SILKE_ENTANGLEMENT_DISTANCES_MAX;
    public static final ForgeConfigSpec.ConfigValue<Integer> SUSPICIOUS_SPIDER_SILKE_ENTANGLEMENT_DISTANCES_MIN;
    public static final ForgeConfigSpec.ConfigValue<Integer> SUSPICIOUS_SPIDER_SILKE_ENTANGLEMENT_PREPAER_TIME;
    public static final ForgeConfigSpec.ConfigValue<Integer> SUSPICIOUS_SPIDER_SILKE_ENTANGLEMENT_DO_HURT_TICK;

    static {
        BUILDER.push("myMod config");
        BUILDER.push("rage effect");
        RAGE_TARGET_EFFECT_DURATION_TICK=BUILDER.defineInRange("rage effect duration tick",35,1,60);
        RAGE_TARGET_EFFECT_SPEEDUP_LEVEL=BUILDER.comment("monster speed up level while range").define("speed up leve",4);
        BUILDER.pop();

        BUILDER.push("SUSPICIOUS_CREEPER");
        SUSPICIOUS_CREEPER_RAGE_EFFECT_DURATION_TIME=BUILDER.defineInRange("rage effect duration time",300,120,300);
        SUSPICIOUS_CREEPER_INJECT_TIME=BUILDER.define("SUSPICIOUS_CREEPER inject time",4);
        SUSPICIOUS_CREEPER_EXPLODE_RADIUS=BUILDER.defineInRange("SUSPICIOUS_CREEPER explode radius",7,3,11);
        SUSPICIOUS_CREEPER_AREA_EFFECT_RADIUS=BUILDER.defineInRange("SUSPICIOUS_CREEPER area effect radius",7,3,11);
        SUSPICIOUS_CREEPER_AREA_EFFECT_DURATION_TIME=BUILDER.defineInRange("SUSPICIOUS_CREEPER area effect duration time",1,1,5);
        BUILDER.pop();

        BUILDER.push("SUSPICIOUS_ENDERMAN");
        SUSPICIOUS_ENDERMAN_AREA_EFFECT_RADIUS=BUILDER.defineInRange("SUSPICIOUS_ENDERMAN area effect radius",10,3,11);
        SUSPICIOUS_ENDERMAN_AREA_EFFECT_DURATION_TIME=BUILDER.defineInRange("SUSPICIOUS_ENDERMAN area effect duration time",5,1,5);
        SUSPICIOUS_ENDERMAN_HARMFUL_EFFECT_DURATION_TIME=BUILDER.defineInRange("SUSPICIOUS_ENDERMAN darkness duration time",2,1,4);
        SUSPICIOUS_ENDERMAN_HURT_TICK=BUILDER.defineInRange("SUSPICIOUS_ENDERMAN do hurt tick",1,1,3);
        SUSPICIOUS_ENDERMAN_PREPARE_TICK=BUILDER.comment("actually half of this value ").defineInRange("SUSPICIOUS_ENDERMAN prepare tick",1,1,5);
        BUILDER.pop();

        BUILDER.push("SUSPICIOUS_SLIME");
        SUSPICIOUS_SLIME_ACIDITY_AREA_EFFECT_RADIUS=BUILDER.defineInRange("SUSPICIOUS_SLIME acidity area effect radius",5,3,7);
        SUSPICIOUS_SLIME_ACIDITY_AREA_EFFECT_DURATION_TIME=BUILDER.defineInRange("SUSPICIOUS_SLIME acidity area effect duration time",20,15,30);
        SUSPICIOUS_SLIME_ACIDITY_DISTANCE=BUILDER.defineInRange("SUSPICIOUS_SLIME acidity distance",5,5,11);
        SUSPICIOUS_SLIME_PREPARE_TICK=BUILDER.defineInRange("SUSPICIOUS_SLIME acidity prepare tick",4,3,5);
        SUSPICIOUS_SLIME_TRANSLATE_TICK=BUILDER.defineInRange("SUSPICIOUS_SLIME translate tick",30,15,45);
        BUILDER.pop();

        BUILDER.push("SUSPICIOUS_SPIDER");
        SUSPICIOUS_SPIDER_SILKE_ENTANGLEMENT_DISTANCES_MAX=BUILDER.defineInRange("SUSPICIOUS_SPIDER attack radius max",18,3,20);
        SUSPICIOUS_SPIDER_SILKE_ENTANGLEMENT_DISTANCES_MIN=BUILDER.defineInRange("SUSPICIOUS_SPIDER attack radius min",3,1,20);
        SUSPICIOUS_SPIDER_SILKE_ENTANGLEMENT_PREPAER_TIME=BUILDER.defineInRange("SUSPICIOUS_SLIME attack prepare time",2,1,4);
        SUSPICIOUS_SPIDER_SILKE_ENTANGLEMENT_DO_HURT_TICK=BUILDER.defineInRange("SUSPICIOUS_SLIME do hurt tick",2,1,3);
        BUILDER.pop();

        BUILDER.pop();
        SPEC=BUILDER.build();
    }
}
