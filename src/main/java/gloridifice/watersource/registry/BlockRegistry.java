package gloridifice.watersource.registry;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.block.CoconutBlock;
import gloridifice.watersource.common.block.CoconutTreeHeadBlock;
import gloridifice.watersource.common.block.WaterFilterBlock;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import roito.afterthedrizzle.common.item.NormalBlockItem;

public class BlockRegistry extends RegistryModule{
    public static final DeferredRegister<Block> FLUID_BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, WaterSource.MODID);

    public final static Block blockWaterFilter = new WaterFilterBlock("wooden_water_filter", Block.Properties.create(Material.WOOD).sound(SoundType.WOOD));
    public final static Block blockCoconutTreeLog = new LogBlock(MaterialColor.WOOD, Block.Properties.create(Material.WOOD).sound(SoundType.WOOD)).setRegistryName("coconut_tree_log");
    public final static Block blockCoconutTreeHead = new CoconutTreeHeadBlock("coconut_tree_head",Block.Properties.create(Material.WOOD).sound(SoundType.WOOD));
    public final static Block blockCoconutTreeLeaf = new LeavesBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT).notSolid()).setRegistryName("coconut_tree_leaf");
    public final static Block blockCoconut = new CoconutBlock(Block.Properties.create(Material.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(0.4F).sound(SoundType.WOOD).notSolid()).setRegistryName("coconut");

    public final static BlockItem itemCoconut = new NormalBlockItem(blockCoconut, new Item.Properties().group(GroupRegistry.waterSourceGroup));
    public final static BlockItem itemCoconutTreeLeaf = new NormalBlockItem(blockCoconutTreeLeaf, new Item.Properties().group(GroupRegistry.waterSourceGroup));
    public final static BlockItem itemCoconutTreeHead = new NormalBlockItem(blockCoconutTreeHead, new Item.Properties().group(GroupRegistry.waterSourceGroup));
    public final static BlockItem itemWaterFilter = new NormalBlockItem(blockWaterFilter, new Item.Properties().group(GroupRegistry.waterSourceGroup));
    public final static BlockItem itemCoconutLog = new NormalBlockItem(blockCoconutTreeLog, new Item.Properties().group(GroupRegistry.waterSourceGroup));

    //Fluid
    public static RegistryObject<FlowingFluidBlock> blockPurifiedWaterFluid = FLUID_BLOCKS.register("purified_water_fluid", () -> {
        return new FlowingFluidBlock(FluidRegistry.purifiedWaterFluid, Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops());
    });

    public BlockRegistry(){
        super();
        BlockRegistry.FLUID_BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}

