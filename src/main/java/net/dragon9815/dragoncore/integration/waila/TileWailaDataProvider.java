package net.dragon9815.dragoncore.integration.waila;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public final class TileWailaDataProvider implements IWailaDataProvider {
    private final List<IWailaDataProvider> providers;

    public TileWailaDataProvider() {
        this.providers = new ArrayList<IWailaDataProvider>();
    }

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor iWailaDataAccessor, IWailaConfigHandler iWailaConfigHandler) {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currentToolTip, IWailaDataAccessor iWailaDataAccessor, IWailaConfigHandler iWailaConfigHandler) {
        for (IWailaDataProvider provider : this.providers) {
            provider.getWailaHead(itemStack, currentToolTip, iWailaDataAccessor, iWailaConfigHandler);
        }

        return currentToolTip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currentToolTip, IWailaDataAccessor iWailaDataAccessor, IWailaConfigHandler iWailaConfigHandler) {
        for (IWailaDataProvider provider : this.providers) {
            provider.getWailaBody(itemStack, currentToolTip, iWailaDataAccessor, iWailaConfigHandler);
        }

        //LogHelper.info(String.format(">>> X:%d, Y:%d, Z:%d", iWailaDataAccessor.getPosition().blockX, iWailaDataAccessor.getPosition().blockY, iWailaDataAccessor.getPosition().blockZ));

        return currentToolTip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currentToolTip, IWailaDataAccessor iWailaDataAccessor, IWailaConfigHandler iWailaConfigHandler) {
        for (IWailaDataProvider provider : this.providers) {
            provider.getWailaTail(itemStack, currentToolTip, iWailaDataAccessor, iWailaConfigHandler);
        }

        return currentToolTip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity tileEntity, NBTTagCompound tag, World world, int x, int y, int z) {
        for (IWailaDataProvider provider : this.providers) {
            provider.getNBTData(player, tileEntity, tag, world, x, y, z);
        }

        return tag;
    }
}
