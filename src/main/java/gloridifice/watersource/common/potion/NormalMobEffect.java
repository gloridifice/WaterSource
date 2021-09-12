package gloridifice.watersource.common.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class NormalMobEffect extends MobEffect {
    public NormalMobEffect(String name, MobEffectCategory category, int liquidColorIn) {
        super(category, liquidColorIn);
        this.setRegistryName(name);
    }
}
