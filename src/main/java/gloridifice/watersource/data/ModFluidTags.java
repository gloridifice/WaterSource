package gloridifice.watersource.data;

import gloridifice.watersource.WaterSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

public class ModFluidTags {

    public static final TagKey<Fluid> DRINK =
            register("drink");

    private static TagKey<Fluid> register(String name)
    {
        return FluidTags.create(new ResourceLocation(WaterSource.MODID, name));
    }
}
