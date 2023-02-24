package watersource.world.recipe.type

import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.Container
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.level.Level
import net.minecraft.world.level.material.Fluid
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.FluidUtil
import watersource.WaterSource
import watersource.world.recipe.ModRecipeSerializers
import watersource.world.recipe.ModRecipeTypes
import kotlin.jvm.optionals.getOrNull

class WaterLevelRecipe : Recipe<Container> {
    var recipeGroup = ""
    var sort = WaterLevelRecipeSort.ITEM
    var water = 0f
    var saturationModifier = 0f
    var tag = ""

    //item
    var item : Item? = null
    var nbt: CompoundTag? = null;

    //fluid
    var fluid : Fluid? = null


    companion object{
        fun createItemRecipe(item: Item?, tag : String = "", water: Float = 0f, saturationModifier: Float = 0f, nbt: CompoundTag? = null) : WaterLevelRecipe{
            var recipe = WaterLevelRecipe()
            recipe.sort = WaterLevelRecipeSort.ITEM

            recipe.water = water
            recipe.item = item
            recipe.nbt = nbt
            recipe.saturationModifier = saturationModifier
            return recipe
        }
        fun createFluidRecipe(fluid: Fluid?, tag : String = "", water: Float = 0f, unitSaturationModifier: Float = 0f) : WaterLevelRecipe{
            var recipe = WaterLevelRecipe()
            recipe.sort = WaterLevelRecipeSort.ITEM

            recipe.fluid = fluid
            recipe.water = water
            recipe.saturationModifier = unitSaturationModifier
            return recipe
        }
    }

    fun match(itemStack: ItemStack) : Boolean{
        when(sort){
            WaterLevelRecipeSort.ITEM -> {
                if (itemStack.isEmpty) return false
                if (!itemStack.`is`(this.item)) return false
                if (this.nbt != null){
                    if (itemStack.serializeNBT() == null) return false

                    val ourNbt = this.nbt!!
                    val itemTag =  itemStack.serializeNBT()!!
                    for (ourKey in ourNbt.allKeys){
                        if (!itemTag.contains(ourKey)) {
                            return false
                        }else if (!itemTag[ourKey]!!.equals(ourNbt[ourKey])) return false
                    }
                }
                return true
            }
            WaterLevelRecipeSort.FLUID -> {
                val fluid : FluidStack? = FluidUtil.getFluidContained(itemStack).getOrNull()
                if (fluid == null || fluid.isEmpty) return false
                return fluid.fluid.isSame(this.fluid)
            }
        }
    }
    override fun getId(): ResourceLocation = ResourceLocation(WaterSource.ID ,"water_level_recipe")
    override fun getSerializer(): RecipeSerializer<*> = ModRecipeSerializers.WATER_LEVEL
    override fun getType(): RecipeType<*> = ModRecipeTypes.WATER_LEVEL
    override fun getGroup(): String = recipeGroup

    //<editor-fold desc="No Use">
    override fun matches(container: Container, level: Level): Boolean = false
    override fun assemble(container: Container): ItemStack = ItemStack.EMPTY
    override fun canCraftInDimensions(p_43999_: Int, p_44000_: Int): Boolean = false
    override fun getResultItem(): ItemStack = ItemStack.EMPTY
    //</editor-fold>
}
enum class WaterLevelRecipeSort{
    ITEM, FLUID
}

