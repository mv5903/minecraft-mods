package net.kwikmatt.mattmod.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.kwikmatt.mattmod.MattMod;
import net.kwikmatt.mattmod.block.MattBlocks;
import net.kwikmatt.mattmod.group.ModItemGroup;
import net.kwikmatt.mattmod.item.custom.FireStickItem;
import net.kwikmatt.mattmod.misc.MattIdentifiers;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * All items to be rendered into the game
 * @author Matthew Vandenberg & Christopher Quesada
 */
@SuppressWarnings("unused")
public class MattItems {
    public static final Item CRIMSON_ORB = registerItem("crimson_orb",
            new Item(new FabricItemSettings().group(ModItemGroup.MATT_GROUP)));

    public static final Item FIRE_STICK = registerItem("fire_stick",
            new FireStickItem(new FabricItemSettings().group(ModItemGroup.MATT_GROUP).maxDamage(128)));

    public static final Item CURSED_BREAD = registerItem("cursed_bread", new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(2).saturationModifier(9f).statusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 0), 1.0f).build())));

    public static final BlockItem JEWISH_TRASHCAN_ITEM;

    static {
        JEWISH_TRASHCAN_ITEM = Registry.register(Registry.ITEM, MattIdentifiers.JEWISH_TRASHCAN, new BlockItem(MattBlocks.JEWISH_TRASHCAN, new Item.Settings().group(ModItemGroup.MATT_GROUP)));
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(MattMod.MOD_ID, name), item);
    }

    public static void registerModItems(){
        MattMod.LOGGER.info("Registering Mod Items for " + MattMod.MOD_ID);
    }
}
