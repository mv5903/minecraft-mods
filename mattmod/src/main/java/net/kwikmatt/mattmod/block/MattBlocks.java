package net.kwikmatt.mattmod.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.kwikmatt.mattmod.MattMod;
import net.kwikmatt.mattmod.group.ModItemGroup;
import net.kwikmatt.mattmod.block.custom.Crimson_Ore_Block;
import net.kwikmatt.mattmod.entity.MattEntities;
import net.kwikmatt.mattmod.inventory.MattBoxBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * All custom blocks included in the MattMod.
 *
 * @author Matthew Vandenberg & Christopher Quesada
 */
@SuppressWarnings("unused")
public class MattBlocks {

    // Add any custom blocks you would like to add to the game here

    public static final Block CRIMSON_BLOCK = registerBlock("crimson", new Block(FabricBlockSettings.of(Material.METAL).strength(6f).requiresTool().sounds(BlockSoundGroup.NETHERITE).lightLevel((5))), ModItemGroup.MATT_GROUP);
    public static final Block CRINGE_BLOCK = registerBlock("cringe", new Block(FabricBlockSettings.of(Material.METAL).strength(10f).requiresTool()), ModItemGroup.MATT_GROUP);
    public static final Block CRIMSON_ORE = registerBlock("crimson_ore", new Crimson_Ore_Block(FabricBlockSettings.of(Material.STONE).strength(2f).requiresTool().sounds(BlockSoundGroup.NETHER_GOLD_ORE)), ModItemGroup.MATT_GROUP);
    public static final Block JEWISH_TRASHCAN = Registry.register(Registry.BLOCK, MattEntities.BOX, new MattBoxBlock(FabricBlockSettings.copyOf(Blocks.CHEST)));

    /**
     * Actively registers the block into the game. Called automatically from the above block constants.
     * @param name In-game name of the block you would like to register
     * @param block The block object you would like to associate the name of the block with
     * @param group The group you would like to add the block to (think of the tabs in the creative inventory, for example
     * @return The official registration of the block into the game.
     */
    public static Block registerBlock(String name, Block block, ItemGroup group) {
        registerBlockItem(name, block, group);
        return Registry.register(Registry.BLOCK, new Identifier(MattMod.MOD_ID, name), block);
    }

    /**
     * The actual registration called implicitly from registerBlock
     */
    public static Item registerBlockItem(String name, Block block, ItemGroup group) {
        return Registry.register(Registry.ITEM, new Identifier(MattMod.MOD_ID, name), new BlockItem(block, new FabricItemSettings().group(group)));
    }

    /**
     * Load this class so that all the Block constants at the start of this file register into the game.
     */
    public static void registerModItems() {
        MattMod.LOGGER.info("Registering Mod Items for " + MattMod.MOD_ID);
    }
}
