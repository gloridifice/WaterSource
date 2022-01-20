package gloridifice.watersource.registry;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.block.*;
import gloridifice.watersource.common.block.grower.CoconutTreeGrower;
import gloridifice.watersource.common.item.EverlastingStrainerBlockItem;
import gloridifice.watersource.common.item.ModNormalBlockItem;
import gloridifice.watersource.common.item.StrainerBlockItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public class BlockRegistry extends RegistryModule {
    public static final DeferredRegister<Block> FLUID_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, WaterSource.MODID);

    public final static Block WOODEN_WATER_FILTER = new WaterFilterBlock("wooden_water_filter", Block.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).sound(SoundType.WOOD).strength(1.0F, 3.0f).noOcclusion());
    public final static Block IRON_WATER_FILTER = new RedstoneWaterFilterBlock("iron_water_filter", Block.Properties.of(Material.METAL, MaterialColor.COLOR_GRAY).sound(SoundType.STONE).strength(2.2F, 3.0f).noOcclusion().requiresCorrectToolForDrops());
    public final static Block PALM_TREE_LOG = log(MaterialColor.WOOD, MaterialColor.WOOD).setRegistryName("palm_tree_log");
    public final static Block PALM_TREE_HEAD = new PalmTreeHeadBlock("palm_tree_head", Block.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).sound(SoundType.WOOD).strength(2.0f, 3.0f).randomTicks());
    public final static Block PALM_TREE_LEAF = new LeavesBlock(Block.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.CROP).requiresCorrectToolForDrops().noOcclusion()).setRegistryName("palm_tree_leaf");//todo
    public final static Block BLOCK_NATURAL_COCONUT = new NaturalCoconutBlock("natural_coconut", Block.Properties.of(Material.WOOD).strength(0.4F).sound(SoundType.WOOD).randomTicks().noOcclusion());
    public final static Block BLOCK_COCONUT = new CoconutBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(0.4F).sound(SoundType.WOOD).randomTicks().noOcclusion(), "coconut");
    public final static Block BLOCK_COCONUT_SAPLING = new PalmTreeSaplingBlock(new CoconutTreeGrower(), Block.Properties.of(Material.PLANT).sound(SoundType.CROP).randomTicks().noCollission()).setRegistryName("palm_tree_sapling");
    //strainers
    public final static Block PRIMITIVE_STRAINER = new StrainerBlock("primitive_strainer", Block.Properties.of(Material.WOOL).sound(SoundType.WOOL).noOcclusion().strength(0.6f));
    public final static Block PAPER_STRAINER = new StrainerBlock("paper_strainer", Block.Properties.of(Material.WOOL).sound(SoundType.WOOL).noOcclusion().strength(0.4f));
    public final static Block DIRTY_STRAINER = new StrainerBlock("dirty_strainer", Block.Properties.of(Material.WOOL).sound(SoundType.WOOL).noOcclusion().strength(0.6f));
    public final static Block PAPER_SOUL_STRAINER = new StrainerBlock("paper_soul_strainer", Block.Properties.of(Material.WOOL).sound(SoundType.WOOL).noOcclusion().strength(0.4f));
    public final static Block SOUL_STRAINER = new StrainerBlock("soul_strainer", Block.Properties.of(Material.WOOL).sound(SoundType.WOOL).noOcclusion().strength(0.6f));
    public final static Block EVERLASTING_STRAINER = new StrainerBlock("everlasting_strainer", Block.Properties.of(Material.WOOL).sound(SoundType.WOOL).noOcclusion().strength(0.6f));
    public final static Block EVERLASTING_SOUL_STRAINER = new StrainerBlock("everlasting_soul_strainer", Block.Properties.of(Material.WOOL).sound(SoundType.WOOL).noOcclusion().strength(0.6f));

    public static final Block PALM_TREE_PLANKS = new Block(Block.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)).setRegistryName("palm_tree_planks");
    public static final Block PALM_TREE_DOOR = new PalmTreeDoorBlock("palm_tree_door", Block.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F).sound(SoundType.WOOD).noOcclusion());
    public static final Block PALM_TREE_TRAPDOOR = new PalmTreeTrapdoor("palm_tree_trapdoor", Block.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F).sound(SoundType.WOOD).noOcclusion());
    public static final Block PALM_TREE_STAIRS = new StairBlock(() -> PALM_TREE_PLANKS.getStateDefinition().any(), Block.Properties.copy(PALM_TREE_PLANKS)).setRegistryName("palm_tree_stairs");
    public static final Block STRIPPED_PALM_TREE_LOG = log(MaterialColor.WOOD, MaterialColor.WOOD).setRegistryName("stripped_palm_tree_log");
    public static final Block PALM_TREE_BUTTON = new ModButtonBlock("palm_tree_button", Block.Properties.of(Material.WOOD).strength(0.5F).sound(SoundType.WOOD));
    public static final Block PALM_TREE_SLAB = new SlabBlock(Block.Properties.copy(PALM_TREE_PLANKS)).setRegistryName("palm_tree_slab");
    public static final Block PALM_TREE_FENCE = new FenceBlock(Block.Properties.copy(PALM_TREE_PLANKS)).setRegistryName("palm_tree_fence");
    public static final Block PALM_TREE_FENCE_GATE = new FenceGateBlock(Block.Properties.copy(PALM_TREE_PLANKS)).setRegistryName("palm_tree_fence_gate");
    public static final Block PALM_TREE_PRESSURE_PLATE = new ModPressurePlateBlock("palm_tree_pressure_plate", PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.copy(PALM_TREE_PLANKS));
    public static final Block STONE_RAIN_COLLECTOR = new RainCollectorBlock("stone_rain_collector", Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0f).noOcclusion());
    //public static final Block BLOCK_WATER_DISPENSER = new WaterDispenserBlock("water_dispenser",AbstractBlock.Properties.of(Material.IRON).noOcclusion());//todo fill properties

    public final static BlockItem ITEM_NATURAL_COCONUT = new ModNormalBlockItem(BLOCK_NATURAL_COCONUT);
    public final static BlockItem ITEM_COCONUT = new ModNormalBlockItem(BLOCK_COCONUT);
    public final static BlockItem ITEM_PALM_TREE_LEAF = new ModNormalBlockItem(PALM_TREE_LEAF);
    public final static BlockItem ITEM_PALM_TREE_HEAD = new ModNormalBlockItem(PALM_TREE_HEAD);
    public final static BlockItem ITEM_WOODEN_WATER_FILTER = new ModNormalBlockItem(WOODEN_WATER_FILTER);
    public final static BlockItem ITEM_IRON_WATER_FILTER = new ModNormalBlockItem(IRON_WATER_FILTER);
    public final static BlockItem ITEM_PALM_TREE_LOG = new ModNormalBlockItem(PALM_TREE_LOG);
    public final static BlockItem ITEM_COCONUT_SAPLING = new ModNormalBlockItem(BLOCK_COCONUT_SAPLING);
    public final static BlockItem ITEM_PRIMITIVE_STRAINER = new StrainerBlockItem(PRIMITIVE_STRAINER, 25);
    public final static BlockItem ITEM_PAPER_STRAINER = new StrainerBlockItem(PAPER_STRAINER, 16);
    public final static BlockItem ITEM_SOUL_STRAINER = new StrainerBlockItem(SOUL_STRAINER, 25);
    public final static BlockItem ITEM_PAPER_SOUL_STRAINER = new StrainerBlockItem(PAPER_SOUL_STRAINER, 16);
    public final static BlockItem ITEM_DIRTY_STRAINER = new StrainerBlockItem(DIRTY_STRAINER);
    public final static BlockItem ITEM_EVERLASTING_STRAINER = new EverlastingStrainerBlockItem(EVERLASTING_STRAINER);
    public final static BlockItem ITEM_EVERLASTING_SOUL_STRAINER = new EverlastingStrainerBlockItem(EVERLASTING_SOUL_STRAINER);

    public final static BlockItem ITEM_PALM_TREE_PLANKS = new ModNormalBlockItem(PALM_TREE_PLANKS);
    public final static BlockItem ITEM_PALM_TREE_DOOR = new ModNormalBlockItem(PALM_TREE_DOOR);
    public final static BlockItem ITEM_PALM_TREE_TRAPDOOR = new ModNormalBlockItem(PALM_TREE_TRAPDOOR);
    public final static BlockItem ITEM_PALM_TREE_STAIRS = new ModNormalBlockItem(PALM_TREE_STAIRS);
    public final static BlockItem ITEM_STRIPPED_PALM_TREE_LOG = new ModNormalBlockItem(STRIPPED_PALM_TREE_LOG);
    public final static BlockItem ITEM_PALM_TREE_BUTTON = new ModNormalBlockItem(PALM_TREE_BUTTON);
    public final static BlockItem ITEM_PALM_TREE_SLAB = new ModNormalBlockItem(PALM_TREE_SLAB);
    public final static BlockItem ITEM_PALM_TREE_FENCE = new ModNormalBlockItem(PALM_TREE_FENCE);
    public final static BlockItem ITEM_PALM_TREE_FENCE_GATE = new ModNormalBlockItem(PALM_TREE_FENCE_GATE);
    public final static BlockItem ITEM_PALM_TREE_PRESSURE_PLATE = new ModNormalBlockItem(PALM_TREE_PRESSURE_PLATE);
    public final static BlockItem ITEM_STONE_FLUID_TANK = new ModNormalBlockItem(STONE_RAIN_COLLECTOR);
    //public final static BlockItem ITEM_WATER_DISPENSER = new ModNormalBlockItem(BLOCK_WATER_DISPENSER);
    //Fluid
    public static RegistryObject<LiquidBlock> BLOCK_PURIFIED_WATER_FLUID = FLUID_BLOCKS.register("purified_water_fluid", () -> {
        return new LiquidBlock(FluidRegistry.PURIFIED_WATER, Block.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops());
    });
    public static RegistryObject<LiquidBlock> BLOCK_SOUL_WATER_FLUID = FLUID_BLOCKS.register("soul_water_fluid", () -> {
        return new LiquidBlock(FluidRegistry.SOUL_WATER, Block.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops());
    });
    public static RegistryObject<LiquidBlock> BLOCK_COCONUT_JUICE_FLUID = FLUID_BLOCKS.register("coconut_juice_fluid", () -> {
        return new LiquidBlock(FluidRegistry.COCONUT_JUICE, Block.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops());
    });

    //摘自原版
    private static RotatedPillarBlock log(MaterialColor p_50789_, MaterialColor p_50790_) {
        return new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, (p_152624_) -> {
            return p_152624_.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? p_50789_ : p_50790_;
        }).strength(2.0F).sound(SoundType.WOOD));
    }
}

