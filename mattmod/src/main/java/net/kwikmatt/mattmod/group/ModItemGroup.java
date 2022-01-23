package net.kwikmatt.mattmod.group;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.kwikmatt.mattmod.MattMod;
import net.kwikmatt.mattmod.item.MattItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

/**
 * Custom tab groupings that show up in places such as the different tabs in the creative inventory
 * @author Matthew Vandenberg & Christopher Quesada
 */
@SuppressWarnings("unused")
public class ModItemGroup {
    public static final ItemGroup MATT_GROUP = FabricItemGroupBuilder.build(new Identifier(MattMod.MOD_ID, "mattgroup"),
            () -> new ItemStack(MattItems.CRIMSON_ORB));
}
