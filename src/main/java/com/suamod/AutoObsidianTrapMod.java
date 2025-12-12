package com.suamod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = "autoobsidiantrap", name = "Auto Obsidian Trap", version = "1.0.0", acceptedMinecraftVersions = "[1.8.9]")
public class AutoObsidianTrapMod {
    public static SimpleNetworkWrapper CHANNEL;

    @SidedProxy(clientSide = "com.alvaro.autoobsidiantrap.ClientProxy", serverSide = "com.alvaro.autoobsidiantrap.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new CombatEvents());
        CHANNEL = NetworkRegistry.INSTANCE.newSimpleChannel("autoobsidiantrap");
        CHANNEL.registerMessage(TriggerTrapHandler.class, TriggerTrapMessage.class, 0, Side.SERVER);
        proxy.init();
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new TrapCommand());
    }
}
