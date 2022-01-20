package net.kwikmatt.mattmod.blocks;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MattBlock{

    public Block block = new Block(FabricBlockSettings.of(Material.METAL).strength(4.0f));
    private String blockName;

    public MattBlock(String blockName) {
        this.blockName = blockName;
    }

    public void addToGame() {
        Registry.register(Registry.BLOCK, new Identifier("mattmod", blockName), this.block);
        Registry.register(Registry.ITEM, new Identifier("mattmod", blockName), new BlockItem(this.block, new FabricItemSettings().group(ItemGroup.MISC)));
    }

    public String toString() {
        return String.format("MattBlock: %s\nBlock: %s", blockName, block.toString());
    }

}
