package gloridifice.watersource.registry;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigRegistry {
    public static ForgeConfigSpec CLIENT_CONFIG,COMMON_CONFIG;
    public static ForgeConfigSpec.BooleanValue OPEN_FOOD_WATER_LEVEL, OPEN_WATER_SATURATION_LEVEL, IS_DEBUG_MODE, RESET_WATER_LEVEL_IN_DEATH;

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        COMMON_BUILDER.push("Display");
        OPEN_WATER_SATURATION_LEVEL = COMMON_BUILDER.comment("It decides if player can see water.json saturation level.","Default:false").define("displayWaterSaturationLevel",false);
        OPEN_FOOD_WATER_LEVEL = COMMON_BUILDER.comment("It decides if player can see foods/drinks' water.json level.","Default:true").define("displayFoodsWaterLevel",true);
        IS_DEBUG_MODE = COMMON_BUILDER.comment("Open debug mode.","Default:false").define("debugMode",false);
        COMMON_BUILDER.pop();
        CLIENT_CONFIG = COMMON_BUILDER.build();
    }
    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        COMMON_BUILDER.push("Misc");
        RESET_WATER_LEVEL_IN_DEATH = COMMON_BUILDER.comment("It decides if players' water.json level would reset in death.","Default:true").define("resetWaterLevelInDeath",true);
        COMMON_BUILDER.pop();
        COMMON_CONFIG = COMMON_BUILDER.build();
    }
}
