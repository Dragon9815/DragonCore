package net.dragon9815.dragoncore.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import net.dragon9815.dragoncore.client.settings.KeyBindings;
import net.dragon9815.dragoncore.handler.KeyEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class ClientProxy extends CommonProxy {
    @Override
    public EntityPlayer getClientPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }

    @Override
    public void registerRenderers() {
    }

    @Override
    public void registerTilEntitySpecialRenderers() {
    }

    @Override
    public void registerKeyBindings() {
        KeyBindings.init();
    }

    @Override
    public void registerEventHandlers() {
        FMLCommonHandler.instance().bus().register(new KeyEventHandler());
    }
}
