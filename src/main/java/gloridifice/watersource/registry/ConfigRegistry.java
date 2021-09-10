package gloridifice.watersource.registry;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigRegistry {
    public static ForgeConfigSpec CLIENT_CONFIG, COMMON_CONFIG;
    public static ForgeConfigSpec.BooleanValue OPEN_FOOD_WATER_LEVEL, OPEN_WATER_SATURATION_LEVEL, IS_DEBUG_MODE, RESET_WATER_LEVEL_IN_DEATH;
    public static ForgeConfigSpec.DoubleValue WATER_REDUCING_RATE, THIRST_DEBUFF_PROBABILITY, POISON_DEBUFF_PROBABILITY;
    public static ForgeConfigSpec.IntValue THIRST_DEBUFF_DURATION, POISON_DEBUFF_DURATION;

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        COMMON_BUILDER.push("Display");
        OPEN_WATER_SATURATION_LEVEL = COMMON_BUILDER.comment(" ", "It decides if player can see water saturation level. Default:false", "决定玩家是否可以看到饱和水分值。默认：false").define("displayWaterSaturationLevel", false);
        OPEN_FOOD_WATER_LEVEL = COMMON_BUILDER.comment(" ", "It decides if player can see items' water level. Default:true", "决定玩家是否可一查看物品的水分值。默认：true").define("displayFoodsWaterLevel", true);
        IS_DEBUG_MODE = COMMON_BUILDER.comment(" ", "Open debug mode. Default:false", "开启调试模式。默认：false").define("debugMode", false);
        COMMON_BUILDER.pop();

        CLIENT_CONFIG = COMMON_BUILDER.build();
    }

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        COMMON_BUILDER.push("Misc");
        RESET_WATER_LEVEL_IN_DEATH = COMMON_BUILDER.comment(" ", "It decides if players' water level would reset in death.","决定玩家死亡时是否回复水分值。默认：true", "Default:true").define("resetWaterLevelInDeath", true);
        WATER_REDUCING_RATE = COMMON_BUILDER.comment(" ", "finalReducingValue = basicValue * waterReducingRate.(DoubleValue)","最终水分值消耗量 = 基础值 * 水分掉落率(本项值).(双浮点类型) 默认：1.0", "Default:1.0").defineInRange("waterReducingRate", 1.0D, 0d, 1000D);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.push("Drink Water Block");
        THIRST_DEBUFF_DURATION = COMMON_BUILDER.comment(" ", "The duration of thirst effect when player drink water from water block.(value * 0.05 = second)  Default:4500", "玩家右键水方块时获得口渴效果的持续时间（这个值 * 0.05 = 秒数）。默认：1200").defineInRange("thirstDebuffDuration", 1200, 0, 10800000);
        THIRST_DEBUFF_PROBABILITY = COMMON_BUILDER.comment(" ", "The probability of thirst effect when player drink water block water block.  Default:0.8", "玩家右键水方块时获得口渴效果的概率。默认：0.8").defineInRange("thirstDebuffProbability", 0.8D, 0D, 1D);
        POISON_DEBUFF_DURATION = COMMON_BUILDER.comment(" ", "The duration of poison effect when player drink water from water block.(value * 0.05 = second)  Default:4500", "玩家右键水方块时获得中毒效果的持续时间（这个值 * 0.05 = 秒数）。默认：200").defineInRange("poisonDebuffDuration", 200, 0, 10800000);
        POISON_DEBUFF_PROBABILITY = COMMON_BUILDER.comment(" ", "The probability of poison effect when player drink water block water block.  Default:0.05", "玩家右键水方块时获得中毒效果的概率。默认：0.05").defineInRange("poisonDebuffProbability", 0.05D, 0D, 1D);
        COMMON_BUILDER.pop();
        COMMON_CONFIG = COMMON_BUILDER.build();
    }
}
