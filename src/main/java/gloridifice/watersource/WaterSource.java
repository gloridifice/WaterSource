package gloridifice.watersource;

import gloridifice.watersource.registry.HUDRegistry;
import gloridifice.watersource.registry.ItemRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("watersource")
public class WaterSource {
    public static final String MODID = "watersource";
    public WaterSource(){
        new ItemRegistry();
    }
}
