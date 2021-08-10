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
import net.minecraft.util.Direction;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public class BlockRegistry extends RegistryModule {
    public static final DeferredRegister<Block> FLUID_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, WaterSource.MODID);

    public final static Block BLOCK_WOODEN_WATER_FILTER = new WaterFilterBlock("wooden_water_filter", Block.Properties.create(Material.WOOD, MaterialColor.BROWN).sound(SoundType.WOOD).hardnessAndResistance(1.0F).harvestTool(ToolType.AXE).notSolid());
    public final static Block BLOCK_IRON_WATER_FILTER = new RedstoneWaterFilterBlock("iron_water_filter", Block.Properties.create(Material.IRON, MaterialColor.GRAY).sound(SoundType.STONE).hardnessAndResistance(2.2F).harvestTool(ToolType.PICKAXE).notSolid());
    public final static Block BLOCK_COCONUT_TREE_LOG = createLogBlock(MaterialColor.WOOD, MaterialColor.OBSIDIAN).setRegistryName("coconut_tree_log");
    public final static Block BLOCK_COCONUT_TREE_HEAD = new CoconutTreeHeadBlock("coconut_tree_head", Block.Properties.create(Material.WOOD, MaterialColor.BROWN).sound(SoundType.WOOD).tickRandomly().harvestTool(ToolType.AXE).hardnessAndResistance(2.0F));
    public final static Block BLOCK_COCONUT_TREE_LEAF = new LeavesBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT).notSolid()).setRegistryName("coconut_tree_leaf");
    public final static Block BLOCK_NATURAL_COCONUT = new NaturalCoconutBlock(Block.Properties.create(Material.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(0.4F).sound(SoundType.WOOD).tickRandomly().notSolid()).setRegistryName("natural_coconut");
    public final static Block BLOCK_COCONUT = new CoconutBlock(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).harvestTool(ToolType.AXE).hardnessAndResistance(0.4F).sound(SoundType.WOOD).tickRandomly().notSolid(), "coconut");
    public final static Block BLOCK_COCONUT_SAPLING = new CoconutSaplingBlock(new CoconutTree(), Block.Properties.create(Material.PLANTS).sound(SoundType.PLANT).tickRandomly()).setRegistryName("coconut_sapling");
    public final static Block BLOCK_PRIMITIVE_STRAINER = new StrainerBlock("primitive_strainer", Block.Properties.create(Material.WOOL).sound(SoundType.CLOTH).notSolid().hardnessAndResistance(0.6f));
    public final static Block BLOCK_PAPER_STRAINER = new StrainerBlock("paper_strainer", Block.Properties.create(Material.WOOL).sound(SoundType.CLOTH).notSolid().hardnessAndResistance(0.4f));
    public final static Block BLOCK_DIRTY_STRAINER = new StrainerBlock("dirty_strainer", Block.Properties.create(Material.WOOL).sound(SoundType.CLOTH).notSolid().hardnessAndResistance(0.6f));
    public final static Block BLOCK_PAPER_SOUL_STRAINER = new StrainerBlock("paper_soul_strainer", Block.Properties.create(Material.WOOL).sound(SoundType.CLOTH).notSolid().hardnessAndResistance(0.4f));
    public final static Block BLOCK_SOUL_STRAINER = new StrainerBlock("soul_strainer", Block.Properties.create(Material.WOOL).sound(SoundType.CLOTH).notSolid().hardnessAndResistance(0.6f));
    public static final Block BLOCK_COCONUT_TREE_PLANKS = new Block(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)).setRegistryName("coconut_tree_planks");
    public static final Block BLOCK_COCONUT_TREE_DOOR = new CoconutTreeDoorBlock("coconut_tree_door", Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(3.0F).sound(SoundType.WOOD).notSolid());
    public static final Block BLOCK_COCONUT_TREE_TRAPDOOR = new CoconutTreeTrapdoor("coconut_tree_trapdoor", Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(3.0F).sound(SoundType.WOOD).notSolid());
    public static final Block BLOCK_COCONUT_TREE_STAIRS = new StairsBlock(() -> BLOCK_COCONUT_TREE_PLANKS.getDefaultState(), Block.Properties.from(BLOCK_COCONUT_TREE_PLANKS)).setRegistryName("coconut_tree_stairs");
    public static final Block BLOCK_STRIPPED_COCONUT_TREE_LOG = createLogBlock(MaterialColor.WOOD, MaterialColor.OBSIDIAN).setRegistryName("stripped_coconut_tree_log");
    public static final Block BLOCK_COCONUT_TREE_BUTTON = new ModButtonBlock("coconut_tree_button", Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD));
    public static final Block BLOCK_COCONUT_TREE_SLAB = new SlabBlock(Block.Properties.from(BLOCK_COCONUT_TREE_PLANKS)).setRegistryName("coconut_tree_slab");
    public static final Block BLOCK_COCONUT_TREE_FENCE = new FenceBlock(Block.Properties.from(BLOCK_COCONUT_TREE_PLANKS)).setRegistryName("coconut_tree_fence");
    public static final Block BLOCK_COCONUT_TREE_FENCE_GATE = new FenceGateBlock(Block.Properties.from(BLOCK_COCONUT_TREE_PLANKS)).setRegistryName("coconut_tree_fence_gate");
    public static final Block BLOCK_COCONUT_TREE_PRESSURE_PLATE = new ModPressurePlateBlock("coconut_tree_pressure_plate", PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.from(BLOCK_COCONUT_TREE_PLANKS));
    public static final Block BLOCK_STONE_RAIN_COLLECTOR = new RainCollectorBlock("stone_rain_collector", Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).harvestLevel(1).hardnessAndResistance(3.5F).notSolid());
    public static final Block BLOCK_WATER_DISPENSER = new WaterDispenserBlock("water_dispenser",AbstractBlock.Properties.create(Material.IRON));//todo fill properties

    public final static BlockItem ITEM_NATURAL_COCONUT = new ModNormalBlockItem(BLOCK_NATURAL_COCONUT);
    public final static BlockItem ITEM_COCONUT = new ModNormalBlockItem(BLOCK_COCONUT);
    public final static BlockItem ITEM_COCONUT_TREE_LEAF = new ModNormalBlockItem(BLOCK_COCONUT_TREE_LEAF);
    public final static BlockItem ITEM_COCONUT_TREE_HEAD = new ModNormalBlockItem(BLOCK_COCONUT_TREE_HEAD);
    public final static BlockItem ITEM_WOODEN_WATER_FILTER = new ModNormalBlockItem(BLOCK_WOODEN_WATER_FILTER);
    public final static BlockItem ITEM_IRON_WATER_FILTER = new ModNormalBlockItem(BLOCK_IRON_WATER_FILTER);
    public final static BlockItem ITEM_COCONUT_TREE_LOG = new ModNormalBlockItem(BLOCK_COCONUT_TREE_LOG);
    public final static BlockItem ITEM_COCONUT_SAPLING = new ModNormalBlockItem(BLOCK_COCONUT_SAPLING);
    public final static BlockItem ITEM_PRIMITIVE_STRAINER = new StrainerBlockItem(BLOCK_PRIMITIVE_STRAINER, 25);
    public final static BlockItem ITEM_PAPER_STRAINER = new StrainerBlockItem(BLOCK_PAPER_STRAINER, 16);
    public final static BlockItem ITEM_SOUL_STRAINER = new StrainerBlockItem(BLOCK_SOUL_STRAINER, 25);
    public final static BlockItem ITEM_PAPER_SOUL_STRAINER = new StrainerBlockItem(BLOCK_PAPER_SOUL_STRAINER, 16);
    public final static BlockItem ITEM_DIRTY_STRAINER = new StrainerBlockItem(BLOCK_DIRTY_STRAINER);
    public final static BlockItem ITEM_COCONUT_TREE_PLANKS = new ModNormalBlockItem(BLOCK_COCONUT_TREE_PLANKS);
    public final static BlockItem ITEM_COCONUT_TREE_DOOR = new ModNormalBlockItem(BLOCK_COCONUT_TREE_DOOR);
    public final static BlockItem ITEM_COCONUT_TREE_TRAPDOOR = new ModNormalBlockItem(BLOCK_COCONUT_TREE_TRAPDOOR);
    public final static BlockItem ITEM_COCONUT_TREE_STAIRS = new ModNormalBlockItem(BLOCK_COCONUT_TREE_STAIRS);
    public final static BlockItem ITEM_STRIPPED_COCONUT_TREE_LOG = new ModNormalBlockItem(BLOCK_STRIPPED_COCONUT_TREE_LOG);
    public final static BlockItem ITEM_COCONUT_TREE_BUTTON = new ModNormalBlockItem(BLOCK_COCONUT_TREE_BUTTON);
    public final static BlockItem ITEM_COCONUT_TREE_SLAB = new ModNormalBlockItem(BLOCK_COCONUT_TREE_SLAB);
    public final static BlockItem ITEM_COCONUT_TREE_FENCE = new ModNormalBlockItem(BLOCK_COCONUT_TREE_FENCE);
    public final static BlockItem ITEM_COCONUT_TREE_FENCE_GATE = new ModNormalBlockItem(BLOCK_COCONUT_TREE_FENCE_GATE);
    public final static BlockItem ITEM_COCONUT_TREE_PRESSURE_PLATE = new ModNormalBlockItem(BLOCK_COCONUT_TREE_PRESSURE_PLATE);
    public final static BlockItem ITEM_STONE_FLUID_TANK = new ModNormalBlockItem(BLOCK_STONE_RAIN_COLLECTOR);
    public final static BlockItem ITEM_WATER_DISPENSER = new ModNormalBlockItem(BLOCK_WATER_DISPENSER);
    //Fluid
    public static RegistryObject<FlowingFluidBlock> BLOCK_PURIFIED_WATER_FLUID = FLUID_BLOCKS.register("purified_water_fluid", () -> {
        return new FlowingFluidBlock(FluidRegistry.PURIFIED_WATER, Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops());
    });
    public static RegistryObject<FlowingFluidBlock> BLOCK_SOUL_WATER_FLUID = FLUID_BLOCKS.register("soul_water_fluid", () -> {
        return new FlowingFluidBlock(FluidRegistry.SOUL_WATER, Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops());
    });
    public static RegistryObject<FlowingFluidBlock> BLOCK_COCONUT_JUICE_FLUID = FLUID_BLOCKS.register("coconut_milk_fluid", () -> {
        return new FlowingFluidBlock(FluidRegistry.COCONUT_MILK, Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops());
    });

    private static RotatedPillarBlock createLogBlock(MaterialColor materialColor0, MaterialColor materialColor1) {
        return new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD, (p_lambda$createLogBlock$36_2_) -> {
            return p_lambda$createLogBlock$36_2_.get(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? materialColor0 : materialColor1;
        }).hardnessAndResistance(2.0F).sound(SoundType.WOOD));
    }
}

