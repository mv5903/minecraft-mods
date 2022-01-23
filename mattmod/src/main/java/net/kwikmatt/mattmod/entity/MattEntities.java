package net.kwikmatt.mattmod.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.kwikmatt.mattmod.MattMod;
import net.kwikmatt.mattmod.block.MattBlocks;
import net.kwikmatt.mattmod.inventory.MattBoxBlockEntity;
import net.kwikmatt.mattmod.inventory.MattBoxScreenHandler;
import net.kwikmatt.mattmod.misc.MattIdentifiers;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.registry.Registry;

/**
 * All other entities items that are added into the game.
 * @author Matthew Vandenberg & Christopher Quesada
 */
@SuppressWarnings("unused")
public class MattEntities {
    // Entity Objects get loaded here
    public static final BlockEntityType<MattBoxBlockEntity> JEWISH_TRASHCAN_ENTITY;

    // Screen handlers get loaded here
    public static final ScreenHandlerType<MattBoxScreenHandler> BOX_SCREEN_HANDLER;

    static {
        BOX_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(MattIdentifiers.JEWISH_TRASHCAN, MattBoxScreenHandler::new);
        JEWISH_TRASHCAN_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, MattIdentifiers.JEWISH_TRASHCAN, FabricBlockEntityTypeBuilder.create(MattBoxBlockEntity::new, MattBlocks.JEWISH_TRASHCAN).build(null));
    }


    public static void registerEntities() {
        MattMod.LOGGER.info("Registering Entities.");
    }
}
