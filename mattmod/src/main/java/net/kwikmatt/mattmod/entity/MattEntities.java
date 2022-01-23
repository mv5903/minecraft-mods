package net.kwikmatt.mattmod.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.kwikmatt.mattmod.MattMod;
import net.kwikmatt.mattmod.block.MattBlocks;
import net.kwikmatt.mattmod.inventory.MattBoxBlockEntity;
import net.kwikmatt.mattmod.inventory.MattBoxScreenHandler;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * All other miscellaneous items that are added into the game.
 * @author Matthew Vandenberg & Christopher Quesada
 */
@SuppressWarnings("unused")
public class MattEntities {
    // Identifiers get loaded here
    public static final Identifier BOX = new Identifier(MattMod.MOD_ID, "box_block");
    // Entity Objects get loaded here
    public static final BlockEntityType<MattBoxBlockEntity> JEWISH_TRASHCAN_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, BOX, FabricBlockEntityTypeBuilder.create(MattBoxBlockEntity::new, MattBlocks.JEWISH_TRASHCAN).build(null));

    // Screen handlers get loaded here
    public static final ScreenHandlerType<MattBoxScreenHandler> BOX_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(BOX, MattBoxScreenHandler::new);

    public static void registerEntities() {
        MattMod.LOGGER.info("Registering Entities.");
    }
}
