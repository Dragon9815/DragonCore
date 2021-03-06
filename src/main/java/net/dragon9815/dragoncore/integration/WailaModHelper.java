package net.dragon9815.dragoncore.integration;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.event.FMLInterModComms;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.dragon9815.dragoncore.block.BlockUpgradeable;
import net.dragon9815.dragoncore.integration.waila.TileWailaDataProvider;

public class WailaModHelper extends BaseModHelper {
    @Optional.Method(modid = "Waila")
    public static void register(IWailaRegistrar registrar) {
        final IWailaDataProvider tile = new TileWailaDataProvider();

        registrar.registerBodyProvider(tile, BlockUpgradeable.class);
        registrar.registerNBTProvider(tile, BlockUpgradeable.class);
    }

    @Override
    protected String getModId() {
        return "Waila";
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void load() throws Exception {
        FMLInterModComms.sendMessage("Waila", "register", this.getClass().getName() + ".register");
    }
}
