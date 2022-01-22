package net.kwikmatt.mattmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.kwikmatt.mattmod.block.MattBlocks;
import net.kwikmatt.mattmod.enchantment.JewishCurseEnchantment;
import net.kwikmatt.mattmod.item.MattItems;
import net.kwikmatt.mattmod.world.gen.feature.ModdedOreConfiguredFeatures;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.kwikmatt.mattmod.world.gen.feature.ModdedOreConfiguredFeatures.CRIMSON_ORE_CONFIGURED_FEATURE;
import static net.kwikmatt.mattmod.world.gen.feature.ModdedOreConfiguredFeatures.CRIMSON_ORE_PLACED_FEATURE;

public class TutorialMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "mattmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static JewishCurseEnchantment JEWISH_ENCHANTMENT = Registry.register(
			Registry.ENCHANTMENT,
			new Identifier("mattmod", "jewish_curse"),
			new JewishCurseEnchantment()
	);

	@Override
	public void onInitialize() {
		MattBlocks.registerModItems();
		MattItems.registerModItems();
		ModdedOreConfiguredFeatures.registerFeatures();

		LOGGER.info("Hello Fabric world!");
	}
}
