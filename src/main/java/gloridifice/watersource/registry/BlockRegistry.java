package gloridifice.watersource.registry;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.block.*;
import gloridifice.watersource.common.block.tree.CoconutTree;
import gloridifice.watersource.common.item.ModNormalBlockItem;
import gloridifice.watersource.common.item.StrainerBlockItem;
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

    public final static Block blockWaterFilter = new WaterFilterBlock("wooden_water_filter", Block.Properties.create(Material.WOOD,MaterialColor.BROWN).sound(SoundType.WOOD).hardnessAndResistance(1.0F).harvestTool(ToolType.AXE));
    public final static Block blockCoconutTreeLog = new LogBlock(MaterialColor.WOOD, Block.Properties.create(Material.WOOD,MaterialColor.BROWN).sound(SoundType.WOOD).hardnessAndResistance(2.0F)).setRegistryName("coconut_tree_log");
    public final static Block blockCoconutTreeHead = new CoconutTreeHeadBlock("coconut_tree_head",Block.Properties.create(Material.WOOD,MaterialColor.BROWN).sound(SoundType.WOOD).tickRandomly().harvestTool(ToolType.AXE));
    public final static Block blockCoconutTreeLeaf = new LeavesBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT).notSolid()).setRegistryName("coconut_tree_leaf");
    public final static Block blockNaturalCoconut = new NaturalCoconutBlock(Block.Properties.create(Material.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(0.4F).sound(SoundType.WOOD).tickRandomly().notSolid()).setRegistryName("natural_coconut");
    public final static Block blockCoconut = new CoconutBlock(Block.Properties.create(Material.WOOD,MaterialColor.BROWN).harvestTool(ToolType.AXE).hardnessAndResistance(0.4F).sound(SoundType.WOOD).tickRandomly().notSolid(),"coconut");
    public final static Block blockCoconutSapling = new CoconutSaplingBlock(new CoconutTree(), Block.Properties.create(Material.PLANTS).sound(SoundType.PLANT)).setRegistryName("coconut_sapling");
    public final static Block blockPrimitiveStrainer = new StrainerBlock("primitive_strainer", Block.Properties.create(Material.WOOL).sound(SoundType.CLOTH).notSolid().hardnessAndResistance(0.6f));
    public final static Block blockPaperStrainer = new StrainerBlock("paper_strainer", Block.Properties.create(Material.WOOL).sound(SoundType.CLOTH).notSolid().hardnessAndResistance(0.4f));
    public final static Block blockDirtyStrainer = new StrainerBlock("dirty_strainer", Block.Properties.create(Material.WOOL).sound(SoundType.CLOTH).notSolid().hardnessAndResistance(0.6f));
    public final static Block blockPaperSoulStrainer = new StrainerBlock("paper_soul_strainer", Block.Properties.create(Material.WOOL).sound(SoundType.CLOTH).notSolid().hardnessAndResistance(0.4f));
    public final static Block blockSoulStrainer = new StrainerBlock("soul_strainer", Block.Properties.create(Material.WOOL).sound(SoundType.CLOTH).notSolid().hardnessAndResistance(0.6f));

    public final static BlockItem itemNaturalCoconut = new ModNormalBlockItem(blockNaturalCoconut);
    public final static BlockItem itemCoconut = new ModNormalBlockItem(blockCoconut);
    public final static BlockItem itemCoconutTreeLeaf = new ModNormalBlockItem(blockCoconutTreeLeaf);
    public final static BlockItem itemCoconutTreeHead = new ModNormalBlockItem(blockCoconutTreeHead);
    public final static BlockItem itemWaterFilter = new ModNormalBlockItem(blockWaterFilter);
    public final static BlockItem itemCoconutTreeLog = new ModNormalBlockItem(blockCoconutTreeLog);
    public final static BlockItem itemCoconutSapling = new ModNormalBlockItem(blockCoconutSapling);
    public final static BlockItem itemPrimitiveStrainer = new StrainerBlockItem(blockPrimitiveStrainer, 25);
    public final static BlockItem itemPaperStrainer = new StrainerBlockItem(blockPaperStrainer, 16);
    public final static BlockItem itemSoulStrainer = new StrainerBlockItem(blockSoulStrainer, 25);
    public final static BlockItem itemPaperSoulStrainer = new StrainerBlockItem(blockPaperSoulStrainer, 16);
    public final static BlockItem itemDirtyStrainer = new StrainerBlockItem(blockDirtyStrainer, 99);
    //Fluid
    public static RegistryObject<FlowingFluidBlock> blockPurifiedWaterFluid = FLUID_BLOCKS.register("purified_water_fluid", () -> {
        return new FlowingFluidBlock(FluidRegistry.purifiedWaterFluid, Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops());
    });
    public static RegistryObject<FlowingFluidBlock> blockSoulWaterFluid = FLUID_BLOCKS.register("soul_water_fluid", () -> {
        return new FlowingFluidBlock(FluidRegistry.soulWaterFluid, Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops());
    });
    public static RegistryObject<FlowingFluidBlock> blockCoconutJuiceFluid = FLUID_BLOCKS.register("coconut_juice_fluid", () -> {
        return new FlowingFluidBlock(FluidRegistry.coconutJuiceFluid, Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops());
    });
    public BlockRegistry(){
        super();
        BlockRegistry.FLUID_BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}

