package watersource.world.capability

import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraftforge.common.capabilities.AutoRegisterCapability
import watersource.world.recipe.WaterLevelRecipeUtil
import kotlin.math.min

@AutoRegisterCapability
class PlayerWaterCapability(var maxWaterLevel: Float){
    var saturationLevel : Float = 5f
    var waterLevel : Float = 20f
    var exhaustionLevel : Float = 0f
    //var lastWaterLevel : Float = 0f
    fun drink(level: Level, itemStack: ItemStack){
        val recipe = WaterLevelRecipeUtil.getRecipe(level, itemStack)
        if (recipe != null){
            waterLevel = min(recipe.water + waterLevel, maxWaterLevel)
            saturationLevel = min(recipe.water * recipe.saturationModifier * 2 + saturationLevel, waterLevel)
        }
    }
}