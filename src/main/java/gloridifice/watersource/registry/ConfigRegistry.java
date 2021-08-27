package gloridifice.watersource.registry;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigRegistry {
    public static ForgeConfigSpec CLIENT_CONFIG, COMMON_CONFIG;
    public static ForgeConfigSpec.BooleanValue OPEN_FOOD_WATER_LEVEL, OPEN_WATER_SATURATION_LEVEL, IS_DEBUG_MODE, RESET_WATER_LEVEL_IN_DEATH;
    public static ForgeConfigSpec.DoubleValue WATER_REDUCING_RATE;

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        COMMON_BUILDER.push("Display");
        OPEN_WATER_SATURATION_LEVEL = COMMON_BUILDER.comment(" ", "It decides if player can see water saturation level.", "决定玩家是否可以看到饱和水分值。默认：false", "Default:false").define("displayWaterSaturationLevel", false);
        OPEN_FOOD_WATER_LEVEL = COMMON_BUILDER.comment(" ", "It decides if player can see items' water level.", "决定玩家是否可一查看物品的水分值。默认：true", "Default:true").define("displayFoodsWaterLevel", true);
        IS_DEBUG_MODE = COMMON_BUILDER.comment(" ", "Open debug mode.", "开启调试模式。默认：false", "Default:false").define("debugMode", false);
        COMMON_BUILDER.pop();
        CLIENT_CONFIG = COMMON_BUILDER.build();
    }

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        COMMON_BUILDER.push("Misc");
        RESET_WATER_LEVEL_IN_DEATH = COMMON_BUILDER.comment(" ", "It decides if players' water level would reset in death.","决定玩家死亡时是否回复水分值。默认：true", "Default:true").define("resetWaterLevelInDeath", true);
        WATER_REDUCING_RATE = COMMON_BUILDER.comment(" ", "finalReducingValue = basicValue * waterReducingRate.(DoubleValue)","最终水分值消耗量 = 基础值 * 水分掉落率(本项值).(双浮点类型) 默认：1.0", "Default:1.0").defineInRange("waterReducingRate", 1.0D, 0d, 1000D);
        COMMON_BUILDER.pop();
        COMMON_CONFIG = COMMON_BUILDER.build();
    }
}
