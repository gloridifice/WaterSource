package watersource.data.tag

import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.ItemTags
import watersource.WaterSource

object ModTags {
    object Item{
        val DRINKABLE = ItemTags.create(ResourceLocation(WaterSource.ID, "drinkable"))
    }
    object Block{

    }
    object Fluid{

    }
}