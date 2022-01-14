package gloridifice.watersource.registry;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public final class RegisterManager {
    public static List<Item> ITEMS = new ArrayList<>();
    public static List<Block> BLOCKS = new ArrayList<>();
    public static List<BlockEntityType<?>> TILE_ENTITY_TYPES = new ArrayList<>();
    public static List<EntityType<?>> ENTITY_TYPES = new ArrayList<>();
    public static List<MobEffect> EFFECTS = new ArrayList<>();
    //public static List<ContainerType<?>> CONTAINER_TYPES = new ArrayList<>();
    public static List<Feature<?>> FEATURES = new ArrayList<>();
    public static List<SoundEvent> SOUNDS = new ArrayList<>();
    public static List<ParticleType<?>> PARTICLES = new ArrayList<>();

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(ITEMS.toArray(new Item[0]));

    }

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(BLOCKS.toArray(new Block[0]));

    }

    @SubscribeEvent
    public static void registerBlockEntityTypes(final RegistryEvent.Register<BlockEntityType<?>> event) {
        event.getRegistry().registerAll(TILE_ENTITY_TYPES.toArray(new BlockEntityType<?>[0]));

    }

    @SubscribeEvent
    public static void registerEntityTypes(final RegistryEvent.Register<EntityType<?>> event) {
        event.getRegistry().registerAll(ENTITY_TYPES.toArray(new EntityType<?>[0]));

    }

    @SubscribeEvent
    public static void registerEffects(final RegistryEvent.Register<MobEffect> event) {
        event.getRegistry().registerAll(EFFECTS.toArray(new MobEffect[0]));

    }

/*    @SubscribeEvent
    public static void registerContainerTypes(RegistryEvent.Register<ContainerType<?>> event) {
        event.getRegistry().registerAll(CONTAINER_TYPES.toArray(new ContainerType<?>[0]));

    }*/

    @SubscribeEvent
    public static void registerFeatures(final RegistryEvent.Register<Feature<?>> event) {
        event.getRegistry().registerAll(FEATURES.toArray(new Feature<?>[0]));

    }

    @SubscribeEvent
    public static void registerSounds(final RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().registerAll(SOUNDS.toArray(new SoundEvent[0]));

    }

    @SubscribeEvent
    public static void registerParticles(final RegistryEvent.Register<ParticleType<?>> event) {
        event.getRegistry().registerAll(PARTICLES.toArray(new ParticleType[0]));
    }

    public static void clearAll() {
        ITEMS = null;
        BLOCKS = null;
        TILE_ENTITY_TYPES = null;
        ENTITY_TYPES = null;
        //CONTAINER_TYPES = null;
        EFFECTS = null;
        FEATURES = null;
        SOUNDS = null;
        PARTICLES = null;
    }
}
