package net.kwikmatt.mattmod;

import net.fabricmc.api.ModInitializer;
import net.kwikmatt.mattmod.block.MattBlocks;
import net.kwikmatt.mattmod.enchantment.MattEnchantments;
import net.kwikmatt.mattmod.entity.MattEntities;
import net.kwikmatt.mattmod.item.MattItems;
import net.kwikmatt.mattmod.world.gen.feature.ModdedOreConfiguredFeatures;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entry point of the Minecraft Server. Everything that needs to get loaded server-side happens here.
 *
 * @author Matthew Vandenberg & Christopher Quesada
 */
public class MattMod implements ModInitializer {
	public static final String MOD_ID = "mattmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	/**
	 * The "main method" of the minecraft server fabric instance.
	 */
	@Override
	public void onInitialize() {
		initItems();
		LOGGER.info("Hello Fabric world from Mattmod!");
	}

	/**
	 * Helper for cleaner code. Makes all method calls to all classes to register their items.
	 */
	private void initItems() {
		MattEntities.registerEntities();
		MattBlocks.registerModItems();
		MattItems.registerModItems();
		ModdedOreConfiguredFeatures.registerFeatures();
		MattEnchantments.registerModEnchantments();

	}

}
