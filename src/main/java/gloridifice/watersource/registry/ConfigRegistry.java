package gloridifice.watersource.registry;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigRegistry {
    public static ForgeConfigSpec CLIENT_CONFIG;
    public static ForgeConfigSpec.BooleanValue OPEN_FOOD_WATER_LEVEL, OPEN_WATER_SATURATION_LEVEL, IS_DEBUG_MODE, RESET_WATER_LEVEL_IN_DEATH;

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        COMMON_BUILDER.comment("View Setting (Client)").push("view");
        OPEN_WATER_SATURATION_LEVEL = COMMON_BUILDER.comment("It decides if player can see WaterSaturationLevel. Default:false").define("enableSeeWaterSaturationLevel",false);
        OPEN_FOOD_WATER_LEVEL = COMMON_BUILDER.comment("It decides if player can see foods/drinks'WaterLevel.It can be true/false. Default:true").define("enableSeeFoodWaterLevel",true);
        IS_DEBUG_MODE = COMMON_BUILDER.comment("Default:false").define("DebugMode",false);
        RESET_WATER_LEVEL_IN_DEATH = COMMON_BUILDER.comment("It decides if players' water level would reset in death. Default:true").define("resetWaterLevelInDeath",true);
        COMMON_BUILDER.pop();
        CLIENT_CONFIG = COMMON_BUILDER.build();
    }
}
