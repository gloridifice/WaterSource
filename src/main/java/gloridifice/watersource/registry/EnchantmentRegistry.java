package gloridifice.watersource.registry;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.enchantment.MoisturizingEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EnchantmentRegistry {
    public static final EquipmentSlot[] ARMOR_SLOTS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, WaterSource.MODID);

    public static final RegistryObject<Enchantment> MOISTURIZING = ENCHANTMENTS.register("moisturizing", () -> new MoisturizingEnchantment(Enchantment.Rarity.UNCOMMON, ARMOR_SLOTS));
}
