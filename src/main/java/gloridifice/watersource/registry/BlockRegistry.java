package gloridifice.watersource.registry;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.block.*;
import gloridifice.watersource.common.block.tree.CoconutTree;
import gloridifice.watersource.common.item.ModNormalBlockItem;
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


public class BlockRegistry extends RegistryModule{
    public static final DeferredRegister<Block> FLUID_BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, WaterSource.MODID);

    public final static Block blockWaterFilter = new WaterFilterBlock("wooden_water_filter", Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(1.0F));
    public final static Block blockCoconutTreeLog = new LogBlock(MaterialColor.WOOD, Block.Properties.create(Material.WOOD).sound(SoundType.WOOD)).setRegistryName("coconut_tree_log");
    public final static Block blockCoconutTreeHead = new CoconutTreeHeadBlock("coconut_tree_head",Block.Properties.create(Material.WOOD).sound(SoundType.WOOD));
    public final static Block blockCoconutTreeLeaf = new LeavesBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT).notSolid()).setRegistryName("coconut_tree_leaf");
    public final static Block blockNaturalCoconut = new NaturalCoconutBlock(Block.Properties.create(Material.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(0.4F).sound(SoundType.WOOD).notSolid()).setRegistryName("natural_coconut");
    public final static Block blockCoconut = new CoconutBlock(Block.Properties.create(Material.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(0.4F).sound(SoundType.WOOD).notSolid(),"coconut");
    public final static Block blockCoconutSapling = new CoconutSaplingBlock(new CoconutTree(), Block.Properties.create(Material.PLANTS).sound(SoundType.PLANT)).setRegistryName("coconut_sapling");

    public final static BlockItem itemNaturalCoconut = new ModNormalBlockItem(blockNaturalCoconut);
    public final static BlockItem itemCoconut = new ModNormalBlockItem(blockCoconut, new Item.Properties().group(GroupRegistry.waterSourceGroup));
    public final static BlockItem itemCoconutTreeLeaf = new ModNormalBlockItem(blockCoconutTreeLeaf, new Item.Properties().group(GroupRegistry.waterSourceGroup));
    public final static BlockItem itemCoconutTreeHead = new ModNormalBlockItem(blockCoconutTreeHead, new Item.Properties().group(GroupRegistry.waterSourceGroup));
    public final static BlockItem itemWaterFilter = new ModNormalBlockItem(blockWaterFilter, new Item.Properties().group(GroupRegistry.waterSourceGroup));
    public final static BlockItem itemCoconutLog = new ModNormalBlockItem(blockCoconutTreeLog, new Item.Properties().group(GroupRegistry.waterSourceGroup));
    public final static BlockItem itemCoconutSapling = new ModNormalBlockItem(blockCoconutSapling, new Item.Properties().group(GroupRegistry.waterSourceGroup));

    //Fluid
    public static RegistryObject<FlowingFluidBlock> blockPurifiedWaterFluid = FLUID_BLOCKS.register("purified_water_fluid", () -> {
        return new FlowingFluidBlock(FluidRegistry.purifiedWaterFluid, Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops());
    });

    public BlockRegistry(){
        super();
        BlockRegistry.FLUID_BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}

