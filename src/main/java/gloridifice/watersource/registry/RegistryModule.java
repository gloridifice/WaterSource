package gloridifice.watersource.registry;

import gloridifice.watersource.WaterSource;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryModule {
    public static final DeferredRegister<EntityType<?>> MOD_ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, WaterSource.MODID);
    public static final DeferredRegister<SoundEvent> MOD_SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, WaterSource.MODID);
    public static final DeferredRegister<ParticleType<?>> MOD_PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, WaterSource.MODID);

}
