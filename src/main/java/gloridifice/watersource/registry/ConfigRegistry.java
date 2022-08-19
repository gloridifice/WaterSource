package gloridifice.watersource.registry;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigRegistry {
    public static ForgeConfigSpec CLIENT_CONFIG, COMMON_CONFIG;
    public static ForgeConfigSpec.BooleanValue
            OPEN_FOOD_WATER_LEVEL, OPEN_WATER_SATURATION_LEVEL, IS_DEBUG_MODE,
            RESET_WATER_LEVEL_IN_DEATH, ENABLE_PALM_TREE_GEN, GIVE_GUIDE_BOOK_ON_JOINING_GAME;
    public static ForgeConfigSpec.DoubleValue WATER_REDUCING_RATE, THIRST_DEBUFF_PROBABILITY, POISON_DEBUFF_PROBABILITY;
    public static ForgeConfigSpec.IntValue THIRST_DEBUFF_DURATION, POISON_DEBUFF_DURATION,
            SLOWNESS_EFFECT_AMPLIFIER, WEAKNESS_EFFECT_AMPLIFIER;

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        COMMON_BUILDER.push("Display");
        OPEN_WATER_SATURATION_LEVEL = COMMON_BUILDER.comment(" ", "It decides if player can see water saturation level.", "Default:false").translation("watersource.config.displayWaterSaturationLevel").define("displayWaterSaturationLevel", false);
        OPEN_FOOD_WATER_LEVEL = COMMON_BUILDER.comment(" ", "It decides if player can see items' water level.", "Default:true").translation("watersource.config.displayFoodsWaterLevel").define("displayFoodsWaterLevel", true);
        IS_DEBUG_MODE = COMMON_BUILDER.comment(" ", "Open debug mode.", "Default:false").translation("watersource.config.debugMode").define("debugMode", false);
        COMMON_BUILDER.pop();
        CLIENT_CONFIG = COMMON_BUILDER.build();
    }

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        COMMON_BUILDER.push("Misc");
        RESET_WATER_LEVEL_IN_DEATH = COMMON_BUILDER.comment(" ", "It decides if players' water level would reset in death.", "Default:true").translation("watersource.config.resetWaterLevelInDeath").define("resetWaterLevelInDeath", true);
        WATER_REDUCING_RATE = COMMON_BUILDER.comment(" ", "finalReducingValue = basicValue * waterReducingRate.(DoubleValue)", "Default:1.0").translation("watersource.config.waterReducingRate").defineInRange("waterReducingRate", 1.0D, 0d, 1000D);
        GIVE_GUIDE_BOOK_ON_JOINING_GAME = COMMON_BUILDER.comment(" ").translation("watersource.config.giveGuideBookOnJoiningGame").define("giveGuideBookOnJoiningGame",true);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.push("Drink Water Block");
        THIRST_DEBUFF_DURATION = COMMON_BUILDER.comment(" ", "The duration of thirst effect when player drink water from water block.(value * 0.05 = seconds)  Default:1200").translation("watersource.config.thirstDebuffDuration").defineInRange("thirstDebuffDuration", 1200, 0, 10800000);
        THIRST_DEBUFF_PROBABILITY = COMMON_BUILDER.comment(" ", "The probability of thirst effect when player drink water block water block.  Default:0.8").translation("watersource.config.thirstDebuffProbability").defineInRange("thirstDebuffProbability", 0.8D, 0D, 1D);
        POISON_DEBUFF_DURATION = COMMON_BUILDER.comment(" ", "The duration of poison effect when player drink water from water block.(value * 0.05 = seconds)  Default:200").translation("watersource.config.poisonDebuffDuration").defineInRange("poisonDebuffDuration", 200, 0, 10800000);
        POISON_DEBUFF_PROBABILITY = COMMON_BUILDER.comment(" ", "The probability of poison effect when player drink water block water block.  Default:0.05").translation("watersource.config.poisonDebuffProbability").defineInRange("poisonDebuffProbability", 0.05D, 0D, 1D);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.push("Effect Punishment");
        COMMON_BUILDER.comment("Tips: Weakness effect's amplifier do plus 1 in Difficulty mode. Amplifier 0 means level 1, and so on.").translation("watersource.config.tipsAmplifier");
        SLOWNESS_EFFECT_AMPLIFIER = COMMON_BUILDER.comment("  ", "It is the slowness effect amplifier of the effect punishment when player's water level is too low. -1 means canceling this effect. Default:0").translation("watersource.config.slownessEffectAmplifier").defineInRange("slownessEffectAmplifier", 0, -1, 999999);
        WEAKNESS_EFFECT_AMPLIFIER = COMMON_BUILDER.comment("  ", "It is the weakness effect amplifier of the effect punishment when player's water level is too low. -1 means canceling this effect. Default:0").translation("watersource.config.weaknessEffectAmplifier").defineInRange("weaknessEffectAmplifier", 0, -1, 999999);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.push("Generation");
        ENABLE_PALM_TREE_GEN = COMMON_BUILDER.comment("  ", "Enable generating palm tree.", "Default:tree").translation("watersource.config.enablePalmTreeGen").define("enablePalmTreeGen", true);
        COMMON_BUILDER.pop();
        COMMON_CONFIG = COMMON_BUILDER.build();
    }
}
