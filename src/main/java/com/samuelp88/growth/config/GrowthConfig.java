package com.samuelp88.growth.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class GrowthConfig {

    private static final ForgeConfigSpec.Builder CONFIG_BUILDER = new ForgeConfigSpec.Builder();
    private static final GrowthConfig CONFIG = new GrowthConfig(CONFIG_BUILDER);
    public static final ForgeConfigSpec CONFIG_SPEC = CONFIG_BUILDER.build();

    public static ForgeConfigSpec.BooleanValue ignoreBonemealable;
    public static ForgeConfigSpec.BooleanValue ignoreBonemealableStrong;

    public GrowthConfig(ForgeConfigSpec.Builder builder) {
        builder.push("Growth Potions Config");

        ignoreBonemealable = builder
                .comment("If the Growth Potion should ignore if a plant is bonemealable or not.")
                .define("ignoreBonemealable", false);
        ignoreBonemealableStrong = builder
                .comment("If the Strong Growth Potion should ignore if a plant is bonemealable or not.")
                .define("ignoreBonemealableStrong", false);

        builder.pop();
    }

}
