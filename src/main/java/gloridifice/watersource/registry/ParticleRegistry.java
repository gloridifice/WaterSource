package gloridifice.watersource.registry;

import net.minecraft.particles.BasicParticleType;

public class ParticleRegistry extends RegistryModule{
    public static final BasicParticleType FLUID_WATER = register("fluid_water",true);

    private static BasicParticleType register(String key, boolean alwaysShow) {
        return (BasicParticleType) new BasicParticleType(alwaysShow).setRegistryName(key);
    }
}
