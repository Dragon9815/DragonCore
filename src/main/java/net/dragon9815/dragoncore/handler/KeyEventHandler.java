package net.dragon9815.dragoncore.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import net.dragon9815.dragoncore.client.settings.KeyBindings;
import net.dragon9815.dragoncore.helpers.LogHelper;

public class KeyEventHandler {

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (KeyBindings.testKey.isPressed()) {
            LogHelper.info(">>> Pressed Test-Key");
        }
    }
}
