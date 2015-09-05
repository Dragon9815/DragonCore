package net.dragon9815.dragoncore.client.settings;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class KeyBindings {

    public static KeyBinding testKey;

    public static void init() {
        testKey = new KeyBinding("key.test", Keyboard.KEY_X, "key.categories.dragoncore");

        ClientRegistry.registerKeyBinding(testKey);
    }
}
