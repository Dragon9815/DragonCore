package net.dragon9815.dragoncore.proxy;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public abstract class CommonProxy implements IProxy, IGuiHandler {

    public abstract EntityPlayer getClientPlayer();

    public void intermodComm() {
    }

    public void registerEventHandlers() {
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    // CLIENT only
    public void registerRenderers() {
    }

    public void registerKeyBindings() {
    }

    public void registerTilEntitySpecialRenderers() {
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }
}
