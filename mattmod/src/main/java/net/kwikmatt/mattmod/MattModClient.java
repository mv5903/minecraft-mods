package net.kwikmatt.mattmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.kwikmatt.mattmod.entity.MattEntities;
import net.kwikmatt.mattmod.inventory.MattBoxScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.LiteralText;
import org.lwjgl.glfw.GLFW;

/**
 * Sometimes, there is some content that only needs to be loaded client side. Same idea as MattMod.
 */
@Environment(EnvType.CLIENT)
public class MattModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(MattEntities.BOX_SCREEN_HANDLER, MattBoxScreen::new);
        KeyBinding binding1 = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.fabric-key-binding-api-v1-testmod.test_keybinding_1", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_APOSTROPHE, "key.mattmod.test"));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (binding1.wasPressed()) {
                client.player.sendMessage(new LiteralText("Key 1 was pressed!"), false);
            }
        });
    }
}
