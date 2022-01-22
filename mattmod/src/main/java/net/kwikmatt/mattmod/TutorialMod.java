package net.kwikmatt.mattmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.kwikmatt.mattmod.block.MattBlocks;
import net.kwikmatt.mattmod.item.MattItems;
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


	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution
		MattBlocks.registerModItems();
		MattItems.registerModItems();

		//write method in ModdedOreConfiguredFeatures class so that all we have to do is register the features using a method like the ones above instead of using all the lines below
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,
				new Identifier(TutorialMod.MOD_ID, "crimson_ore"), CRIMSON_ORE_CONFIGURED_FEATURE);
		Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(TutorialMod.MOD_ID, "crimson_ore"),
				CRIMSON_ORE_PLACED_FEATURE);
		BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), GenerationStep.Feature.UNDERGROUND_ORES,
				RegistryKey.of(Registry.PLACED_FEATURE_KEY,
						new Identifier(TutorialMod.MOD_ID, "crimson_ore")));


		LOGGER.info("Hello Fabric world!");
	}
}
