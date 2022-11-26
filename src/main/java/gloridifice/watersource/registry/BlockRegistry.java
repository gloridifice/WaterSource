package gloridifice.watersource.registry;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.block.*;
import gloridifice.watersource.common.block.grower.CoconutTreeGrower;
import gloridifice.watersource.common.item.EverlastingStrainerBlockItem;
import gloridifice.watersource.common.item.ModNormalBlockItem;
import gloridifice.watersource.common.item.StrainerBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.function.Function;
import java.util.function.Supplier;


public class BlockRegistry {
    public static final DeferredRegister<Block> FLUID_BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS,
                    WaterSource.MODID);
    public final static RegistryObject<WaterFilterBlock> WOODEN_WATER_FILTER =
            registerDefault("wooden_water_filter",
                    () -> new WaterFilterBlock(Block.Properties.of(Material.WOOD,
                            MaterialColor.COLOR_BROWN).sound(SoundType.WOOD)
                            .strength(1.0F, 3.0f).noOcclusion()));
    public final static RegistryObject<RedstoneWaterFilterBlock> IRON_WATER_FILTER =
            registerDefault("iron_water_filter",
                    () -> new RedstoneWaterFilterBlock(Block.Properties.of(Material.METAL,
                            MaterialColor.COLOR_GRAY).sound(SoundType.STONE)
                            .strength(2.2F, 3.0f)
                            .noOcclusion().requiresCorrectToolForDrops()));

    public final static RegistryObject<RotatedPillarBlock> PALM_TREE_LOG =
            registerDefault("palm_tree_log", () -> log(MaterialColor.WOOD, MaterialColor.WOOD));

    public final static RegistryObject<PalmTreeHeadBlock> PALM_TREE_HEAD =
            registerDefault("palm_tree_head",
                    () -> new PalmTreeHeadBlock(Block.Properties.of(Material.WOOD,
                            MaterialColor.COLOR_BROWN).sound(SoundType.WOOD)
                            .strength(2.0f, 3.0f)
                            .randomTicks()));

    public final static RegistryObject<LeavesBlock> PALM_TREE_LEAF =
            registerDefault("palm_tree_leaf",
                    () -> new LeavesBlock(Block.Properties.of(Material.LEAVES)
                    .strength(0.2F).randomTicks()
                    .sound(SoundType.CROP)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()));//todo

    public final static RegistryObject<NaturalCoconutBlock> BLOCK_NATURAL_COCONUT =
            registerDefault("natural_coconut", () ->
            new NaturalCoconutBlock(Block.Properties.of(Material.WOOD)
                    .strength(0.4F)
                    .sound(SoundType.WOOD)
                    .randomTicks().noOcclusion()));

    public final static RegistryObject<CoconutBlock> BLOCK_COCONUT =
            registerDefault("coconut", () ->
            new CoconutBlock(Block.Properties.of(Material.WOOD,
                    MaterialColor.COLOR_BROWN)
                    .strength(0.4F)
                    .sound(SoundType.WOOD)
                    .randomTicks().noOcclusion()));
    public final static RegistryObject<PalmTreeSaplingBlock> BLOCK_COCONUT_SAPLING =
            registerDefault("palm_tree_sapling", () ->
            new PalmTreeSaplingBlock(new CoconutTreeGrower(), Block.Properties.of(Material.PLANT).sound(SoundType.CROP).randomTicks().noCollission()));

    //strainers
    public final static RegistryObject<StrainerBlock> PRIMITIVE_STRAINER =
            registerDefault("primitive_strainer", () ->
            new StrainerBlock(Block.Properties.of(Material.WOOL)
                    .sound(SoundType.WOOL).noOcclusion()
                    .strength(0.6f)));

    public final static RegistryObject<StrainerBlock> PAPER_STRAINER =
            registerDefault("paper_strainer", () ->
            new StrainerBlock(Block.Properties.of(Material.WOOL)
                    .sound(SoundType.WOOL).noOcclusion().strength(0.4f)));

    public final static RegistryObject<StrainerBlock> DIRTY_STRAINER =
            registerDefault("dirty_strainer", () ->
            new StrainerBlock(Block.Properties.of(Material.WOOL)
                    .sound(SoundType.WOOL)
                    .noOcclusion().strength(0.6f)));

    public final static RegistryObject<StrainerBlock> PAPER_SOUL_STRAINER =
            registerDefault("paper_soul_strainer", () ->
            new StrainerBlock(Block.Properties.of(Material.WOOL)
                            .sound(SoundType.WOOL)
                            .noOcclusion().strength(0.4f)));
    public final static RegistryObject<StrainerBlock> SOUL_STRAINER =
            registerDefault("soul_strainer", () ->
            new StrainerBlock(Block.Properties.of(Material.WOOL).sound(SoundType.WOOL)
                            .noOcclusion().strength(0.6f)));

    public final static RegistryObject<StrainerBlock> EVERLASTING_STRAINER =
            registerDefault("everlasting_strainer", () ->
            new StrainerBlock(Block.Properties.of(Material.WOOL
            ).sound(SoundType.WOOL).noOcclusion().strength(0.6f)));

    public final static RegistryObject<StrainerBlock> EVERLASTING_SOUL_STRAINER =
            registerDefault("everlasting_soul_strainer", () ->
            new StrainerBlock(Block.Properties.of(Material.WOOL)
                    .sound(SoundType.WOOL).noOcclusion()
                    .strength(0.6f)));

    public static final RegistryObject<Block> PALM_TREE_PLANKS =
            registerDefault("palm_tree_planks", () ->
            new Block(Block.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));

    public static final RegistryObject<PalmTreeDoorBlock> PALM_TREE_DOOR =
            registerDefault("palm_tree_door", () ->
            new PalmTreeDoorBlock(Block.Properties.of(Material.WOOD,
                    MaterialColor.WOOD)
                    .strength(3.0F)
                    .sound(SoundType.WOOD).noOcclusion()));

    public static final RegistryObject<PalmTreeTrapdoor> PALM_TREE_TRAPDOOR =
            registerDefault("palm_tree_trapdoor", () ->
            new PalmTreeTrapdoor(Block.
                    Properties.of(Material.WOOD, MaterialColor.WOOD)
                    .strength(3.0F)
                    .sound(SoundType.WOOD).noOcclusion()));

    public static final RegistryObject<StairBlock> PALM_TREE_STAIRS =
            registerDefault("palm_tree_stairs", () ->
            new StairBlock(() -> PALM_TREE_PLANKS.get().getStateDefinition().any(),
                    Block.Properties.copy(PALM_TREE_PLANKS.get())));

    public static final RegistryObject<RotatedPillarBlock> STRIPPED_PALM_TREE_LOG =
            registerDefault("stripped_palm_tree_log", () ->
            log(MaterialColor.WOOD, MaterialColor.WOOD));

    public static final RegistryObject<ModButtonBlock> PALM_TREE_BUTTON =
            registerDefault("palm_tree_button", () ->
            new ModButtonBlock(Block.Properties.of(Material.WOOD)
                    .strength(0.5F)
                    .sound(SoundType.WOOD)));

    public static final RegistryObject<SlabBlock> PALM_TREE_SLAB =
            registerDefault("palm_tree_slab", () ->
            new SlabBlock(Block.Properties.copy(PALM_TREE_PLANKS.get())));
    public static final RegistryObject<FenceBlock> PALM_TREE_FENCE =
            registerDefault("palm_tree_fence", () ->
            new FenceBlock(Block.Properties.copy(PALM_TREE_PLANKS.get())));

    public static final RegistryObject<FenceGateBlock> PALM_TREE_FENCE_GATE =
            registerDefault("palm_tree_fence_gate", () ->
            new FenceGateBlock(Block.Properties.copy(PALM_TREE_PLANKS.get())));

    public static final RegistryObject<ModPressurePlateBlock> PALM_TREE_PRESSURE_PLATE =
            registerDefault("palm_tree_pressure_plate", () ->
            new ModPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING,
                    Block.Properties.copy(PALM_TREE_PLANKS.get())));

    public static final RegistryObject<RainCollectorBlock> STONE_RAIN_COLLECTOR =
            registerDefault("stone_rain_collector", () ->
            new RainCollectorBlock(Block.Properties.of(Material.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(3.0F, 3.0f)
                    .noOcclusion()));

    //public static final Block BLOCK_WATER_DISPENSER = new WaterDispenserBlock("water_dispenser",AbstractBlock.Properties.of(Material.IRON).noOcclusion());//todo fill properties

    /**
   public final static BlockItem ITEM_NATURAL_COCONUT =
     new ModNormalBlockItem(BLOCK_NATURAL_COCONUT.get());
    public final static BlockItem ITEM_COCONUT =
            FLUID_ITEMS.register("")
            new ModNormalBlockItem(BLOCK_COCONUT);
    public final static BlockItem ITEM_PALM_TREE_LEAF = new ModNormalBlockItem(PALM_TREE_LEAF);
    public final static BlockItem ITEM_PALM_TREE_HEAD = new ModNormalBlockItem(PALM_TREE_HEAD);
    public final static BlockItem ITEM_WOODEN_WATER_FILTER = new ModNormalBlockItem(WOODEN_WATER_FILTER.get());
    public final static BlockItem ITEM_IRON_WATER_FILTER = new ModNormalBlockItem(IRON_WATER_FILTER.get());
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
    */
    //Fluid
    public static RegistryObject<LiquidBlock> BLOCK_PURIFIED_WATER_FLUID =
            FLUID_BLOCKS.register("purified_water_fluid", () -> {
        return new LiquidBlock(FluidRegistry.PURIFIED_WATER,
                Block.Properties.of(Material.WATER)
                        .noCollission().strength(100.0F).noDrops());
    });
    public static RegistryObject<LiquidBlock> BLOCK_SOUL_WATER_FLUID =
            FLUID_BLOCKS.register("soul_water_fluid", () ->
                    new LiquidBlock(FluidRegistry.SOUL_WATER,
                            Block.Properties.of(Material.WATER)
                                    .noCollission()
                                    .strength(100.0F).noDrops()));
    public static RegistryObject<LiquidBlock> BLOCK_COCONUT_JUICE_FLUID =
            FLUID_BLOCKS.register("coconut_juice_fluid", () ->
       new LiquidBlock(FluidRegistry.COCONUT_JUICE, Block.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops()));

    //摘自原版
    private static RotatedPillarBlock log(MaterialColor p_50789_, MaterialColor p_50790_) {
        return new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, (p_152624_) -> {
            return p_152624_.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? p_50789_ : p_50790_;
        }).strength(2.0F).sound(SoundType.WOOD));
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, @Nullable Function<T, ? extends BlockItem> blockItemFactory) {
        return register(BlockRegistry.FLUID_BLOCKS, ItemRegistry.FLUID_ITEMS, name, blockSupplier, blockItemFactory);
    }

    public static <T extends Block> RegistryObject<T> register(DeferredRegister<Block> blocks, DeferredRegister<Item> items, String name, Supplier<T> blockSupplier, @Nullable Function<T, ? extends BlockItem> blockItemFactory) {
        final String actualName = name.toLowerCase(Locale.ROOT);
        final RegistryObject<T> block = blocks.register(actualName, blockSupplier);
        if (blockItemFactory != null) {
            items.register(actualName, () -> blockItemFactory.apply(block.get()));
        }
        return block;
    }

    private static <T extends Block> RegistryObject<T> registerDefault(String name, Supplier<T> blockSupplier) {
        return register(name, blockSupplier, block -> new BlockItem(block, new Item.Properties().tab(CreativeModeTabRegistry.WATER_SOURCE_TAB)));
    }
}

