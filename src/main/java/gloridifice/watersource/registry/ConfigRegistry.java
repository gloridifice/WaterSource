package gloridifice.watersource.registry;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigRegistry {
    public static ForgeConfigSpec CLIENT_CONFIG;
    public static ForgeConfigSpec.BooleanValue IS_FOOD_WATER_LEVEL_OPEN;
    public static ForgeConfigSpec.BooleanValue IS_WATER_SATURATION_LEVEL_OPEN;
    public static ForgeConfigSpec.BooleanValue IS_TEST_MODE_OPEN;

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        COMMON_BUILDER.comment("View Setting (Client)").push("view");
        IS_WATER_SATURATION_LEVEL_OPEN = COMMON_BUILDER.comment("It decides if player can see WaterSaturationLevel. Default:false").define("canSeeWaterSaturationLevel",false);
        IS_FOOD_WATER_LEVEL_OPEN = COMMON_BUILDER.comment("It decides if player can see foods/drinks'WaterLevel.It can be true/false. Default:true").define("canSeeFoodWaterLevel",true);
        IS_TEST_MODE_OPEN = COMMON_BUILDER.comment("Test data.Please don't open it if you don't want useless date occupy your screen. Default:false").define("openTestMode",false);
        COMMON_BUILDER.pop();
        CLIENT_CONFIG = COMMON_BUILDER.build();
    }
}
