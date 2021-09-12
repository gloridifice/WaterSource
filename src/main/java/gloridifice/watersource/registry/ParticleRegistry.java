package gloridifice.watersource.registry;


import net.minecraft.core.particles.SimpleParticleType;

public class ParticleRegistry extends RegistryModule {
    public static final SimpleParticleType FLUID_WATER = register("fluid_water", true);

    private static SimpleParticleType register(String key, boolean alwaysShow) {
        return (SimpleParticleType) new SimpleParticleType(alwaysShow).setRegistryName(key);
    }
}
