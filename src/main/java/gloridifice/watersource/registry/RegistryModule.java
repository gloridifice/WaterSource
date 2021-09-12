package gloridifice.watersource.registry;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.Feature;

import java.lang.reflect.Field;

public class RegistryModule {
    public RegistryModule() {
        for (Field field : getClass().getFields()) {
            try {
                Object o = field.get(null);
                if (o instanceof Item) {
                    RegisterManager.ITEMS.add((Item) o);
                }
                else if (o instanceof Block) {
                    RegisterManager.BLOCKS.add((Block) o);
                }
                else if (o instanceof BlockEntityType<?>) {
                    RegisterManager.TILE_ENTITY_TYPES.add((BlockEntityType<?>) o);
                }
/*                else if (o instanceof ContainerType<?>) {
                    RegisterManager.CONTAINER_TYPES.add((ContainerType<?>) o);
                }*/
                else if (o instanceof EntityType<?>) {
                    RegisterManager.ENTITY_TYPES.add((EntityType<?>) o);
                }
                else if (o instanceof MobEffect) {
                    RegisterManager.EFFECTS.add((MobEffect) o);
                }
                else if (o instanceof Feature<?>) {
                    RegisterManager.FEATURES.add((Feature<?>) o);
                }
                else if (o instanceof SoundEvent) {
                    RegisterManager.SOUNDS.add((SoundEvent) o);
                }
                else if (o instanceof ParticleType<?>) {
                    RegisterManager.PARTICLES.add((ParticleType<?>) o);
                }
            }
            catch (Exception ignored) {
            }
        }
    }
}
