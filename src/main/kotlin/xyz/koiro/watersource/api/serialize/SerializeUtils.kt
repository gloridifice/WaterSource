package xyz.koiro.watersource.api.serialize

import com.google.gson.JsonObject
import net.minecraft.item.ItemStack
import net.minecraft.nbt.StringNbtReader
import net.minecraft.nbt.visitor.StringNbtWriter
import net.minecraft.registry.Registries
import net.minecraft.util.Identifier
import xyz.koiro.watersource.identifier

object SerializeUtils {
    fun itemStackToJsonObject(itemStack: ItemStack): JsonObject {
        val ret = JsonObject()
        ret.addProperty("item", itemStack.item.identifier().toString())
        if (itemStack.count != 1)
            ret.addProperty("count", itemStack.count)
        if (itemStack.hasNbt())
            ret.addProperty("nbt", StringNbtWriter().apply(itemStack.nbt))
        return ret
    }

    fun jsonObjectToItemStack(jsonObject: JsonObject): ItemStack? {
        val valid = jsonObject.has("item")
        if (valid) {
            val item = Identifier.tryParse(jsonObject.get("item").asString)?.let { Registries.ITEM.get(it) }
            val count = if (jsonObject.has("count")) jsonObject.get("count").asInt else 1
            val nbt = if (jsonObject.has("nbt"))
                StringNbtReader.parse(jsonObject.get("nbt").asString)
            else null
            if (item != null && count > 0) {
                val stack = ItemStack(item, count).apply {
                    if (nbt != null) this.nbt = nbt
                }
                return stack
            }
        }
        return null
    }
}