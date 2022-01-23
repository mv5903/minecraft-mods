package net.kwikmatt.mattmod.enchantment;

import net.kwikmatt.mattmod.MattMod;
import net.kwikmatt.mattmod.enchantment.custom.JewishCurseEnchantment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * All enchantments that are a part of MattMod
 * @author Matthew Vandenberg & Christopher Quesada
 */
@SuppressWarnings("unused")
public class MattEnchantments {
    public static final JewishCurseEnchantment JEWISH_ENCHANTMENT = Registry.register(
            Registry.ENCHANTMENT,
            new Identifier("mattmod", "jewish_curse"),
            new JewishCurseEnchantment()
    );

    public static void registerModEnchantments() {
        MattMod.LOGGER.info("Registering Mod Items for " + MattMod.MOD_ID);
    }
}
