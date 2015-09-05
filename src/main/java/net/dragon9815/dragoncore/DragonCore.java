package net.dragon9815.dragoncore;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.dragon9815.dragoncore.handler.ConfigurationHandler;
import net.dragon9815.dragoncore.helpers.LogHelper;
import net.dragon9815.dragoncore.init.ModBlocks;
import net.dragon9815.dragoncore.init.ModItems;
import net.dragon9815.dragoncore.init.ModTileEntities;
import net.dragon9815.dragoncore.integration.ModRegistry;
import net.dragon9815.dragoncore.network.PacketHandler;
import net.dragon9815.dragoncore.proxy.CommonProxy;
import net.dragon9815.dragoncore.reference.Reference;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, dependencies = "", acceptableRemoteVersions = Reference.VERSION)
public class DragonCore {
    @Mod.Instance(Reference.MOD_ID)
    public static DragonCore instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModTileEntities.init();
        ModBlocks.init();
        ModItems.init();

        PacketHandler.init();

        ConfigurationHandler.init(event.getSuggestedConfigurationFile());

        proxy.registerRenderers();
        proxy.registerKeyBindings();
        proxy.registerEventHandlers();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        ModRegistry.init();
    }
}
