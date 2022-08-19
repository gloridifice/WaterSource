package gloridifice.watersource.common.item.food;

import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties COCONUT_PIECES = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.3F).fast().build();
    public static final FoodProperties COCONUT_CHICKEN = (new FoodProperties.Builder()).nutrition(8).saturationMod(1.25F).fast().build();
    public static final FoodProperties COCONUT_MILK = (new FoodProperties.Builder()).nutrition(1).saturationMod(0F).alwaysEat().fast().build();
}
