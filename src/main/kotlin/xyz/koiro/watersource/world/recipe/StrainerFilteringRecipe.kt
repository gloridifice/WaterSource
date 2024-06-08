package xyz.koiro.watersource.world.recipe

import net.minecraft.inventory.RecipeInputInventory
import net.minecraft.item.ItemStack
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.SpecialCraftingRecipe
import net.minecraft.recipe.book.CraftingRecipeCategory
import net.minecraft.util.Identifier
import net.minecraft.util.collection.DefaultedList
import net.minecraft.world.World
import xyz.koiro.watersource.WSConfig
import xyz.koiro.watersource.world.item.Strainer

abstract class StrainerFilteringRecipe(
    id: Identifier, category: CraftingRecipeCategory,
    val strainer: Ingredient
) :
    SpecialCraftingRecipe(id, category) {
    abstract fun matchInput(stack: ItemStack): Boolean
    abstract fun getCost(ctx: Ctx): Int

    fun getInputAndStrainer(inventory: RecipeInputInventory): Ctx? {
        var strainerStack: ItemStack? = null
        var inputStack: ItemStack? = null
        var sIndex = 0
        var iIndex = 0

        for (i in 0..<inventory.size()) {
            val stack = inventory.getStack(i)
            if (this.strainer.test(stack)) {
                strainerStack = stack
                sIndex = i
            }
            if (matchInput(stack)) {
                inputStack = stack
                iIndex = i
            }
        }
        if (inputStack != null && strainerStack != null)
            return Ctx(strainerStack, inputStack, sIndex, iIndex)
        return null;
    }

    override fun matches(inventory: RecipeInputInventory, world: World): Boolean {
        if (!WSConfig.Filtering.config.enableStrainerRecipe) return false
        getInputAndStrainer(inventory)?.let { ctx ->
            val strainer = ctx.strainer
            return strainer.damage + getCost(ctx) <= strainer.maxDamage
        }
        return false
    }

    override fun getRemainder(inventory: RecipeInputInventory): DefaultedList<ItemStack> {
        val defaultedList = DefaultedList.ofSize(inventory.size(), ItemStack.EMPTY)
        for (i in defaultedList.indices) {
            val item = inventory.getStack(i).item
            if (!item.hasRecipeRemainder()) continue
            defaultedList[i] = ItemStack(item.recipeRemainder)
        }
        getInputAndStrainer(inventory)?.let { ctx ->
            val remained = (ctx.strainer.item as? Strainer)?.getUsedStrainer(ctx.strainer, getCost(ctx)) ?: ItemStack.EMPTY
            defaultedList[ctx.strainerIndex] = remained
        }
        return defaultedList
    }

    override fun fits(width: Int, height: Int): Boolean {
        return width * height >= 2
    }

    data class Ctx(val strainer: ItemStack, val input: ItemStack, val strainerIndex: Int, val inputIndex: Int)
}