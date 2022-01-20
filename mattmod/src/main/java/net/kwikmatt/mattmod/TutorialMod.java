package net.kwikmatt.mattmod;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.kwikmatt.mattmod.blocks.*;

public class TutorialMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "mattmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final MattBlock[] blocks =
	{
			new MattBlock("blocktest"),
			new MattBlock("testblock")
	};


	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution
		for (MattBlock block: blocks) {
			block.addToGame();
		}
		LOGGER.info("Hello Fabric world!");
	}
}
