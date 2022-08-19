package gloridifice.watersource.data;

import gloridifice.watersource.WaterSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class ModFluidTags {
    public static final TagKey<Fluid> DRINK = FluidTags.create(new ResourceLocation(WaterSource.MODID,"drink"));

}
