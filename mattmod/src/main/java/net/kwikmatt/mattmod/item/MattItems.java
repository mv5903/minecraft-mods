package net.kwikmatt.mattmod.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.kwikmatt.mattmod.ModItemGroup;
import net.kwikmatt.mattmod.TutorialMod;
import net.kwikmatt.mattmod.item.custom.FireStickItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.lwjgl.system.CallbackI;

public class MattItems {
    public static final Item CRIMSON_ORB = registerItem("crimson_orb",
            new Item(new FabricItemSettings().group(ModItemGroup.MATT_GROUP)));

    public static final Item FIRE_STICK = registerItem("fire_stick",
            new FireStickItem(new FabricItemSettings().group(ModItemGroup.MATT_GROUP).maxDamage(128)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(TutorialMod.MOD_ID, name), item);
    }

    public static void registerModItems(){
        TutorialMod.LOGGER.info("Registering Mod Items for " + TutorialMod.MOD_ID);

    }
}
