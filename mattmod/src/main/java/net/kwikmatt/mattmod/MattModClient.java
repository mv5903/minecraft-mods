package net.kwikmatt.mattmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.kwikmatt.mattmod.entity.MattEntities;
import net.kwikmatt.mattmod.inventory.MattBoxScreen;

/**
 * Sometimes, there is some content that only needs to be loaded client side. Same idea as MattMod.
 */
@Environment(EnvType.CLIENT)
public class MattModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(MattEntities.BOX_SCREEN_HANDLER, MattBoxScreen::new);
    }
}
