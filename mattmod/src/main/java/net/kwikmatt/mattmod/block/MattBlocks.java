package net.kwikmatt.mattmod.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.kwikmatt.mattmod.ModItemGroup;
import net.kwikmatt.mattmod.TutorialMod;
import net.kwikmatt.mattmod.block.custom.Crimson_Ore_Block;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MattBlocks {
    public static final Block CRIMSON_BLOCK = registerBlock("crimson", new Block(FabricBlockSettings.of(Material.METAL).strength(6f).requiresTool().sounds(BlockSoundGroup.NETHERITE).lightLevel((5))), ModItemGroup.MATT_GROUP);
    public static final Block CRINGE_BLOCK = registerBlock("cringe", new Block(FabricBlockSettings.of(Material.METAL).strength(10f).requiresTool()), ModItemGroup.MATT_GROUP);
    public static final Block CRIMSON_ORE = registerBlock("crimson_ore", new Crimson_Ore_Block(FabricBlockSettings.of(Material.STONE).strength(2f).requiresTool().sounds(BlockSoundGroup.NETHER_GOLD_ORE)), ModItemGroup.MATT_GROUP);

    public static Block registerBlock(String name, Block block, ItemGroup group) {
        registerBlockItem(name, block, group);
        return Registry.register(Registry.BLOCK, new Identifier(TutorialMod.MOD_ID, name), block);
    }

    public static Item registerBlockItem(String name, Block block, ItemGroup group) {
        return Registry.register(Registry.ITEM, new Identifier(TutorialMod.MOD_ID, name), new BlockItem(block, new FabricItemSettings().group(group)));
    }

    public static void registerModItems() {
        TutorialMod.LOGGER.info("Registering Mod Items for " + TutorialMod.MOD_ID);
    }
}
