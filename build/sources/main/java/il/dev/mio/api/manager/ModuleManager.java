//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.api.manager;

import java.util.concurrent.TimeUnit;
import il.dev.mio.Mio;
import il.dev.mio.api.util.IMinecraftUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import il.dev.mio.mod.gui.MioClickGui;
import org.lwjgl.input.Keyboard;
import java.util.Collection;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Comparator;
import il.dev.mio.api.event.events.Render3DEvent;
import il.dev.mio.api.event.events.Render2DEvent;
import java.util.function.Consumer;
import net.minecraftforge.common.MinecraftForge;
import java.util.Arrays;
import java.util.Iterator;
import il.dev.mio.mod.modules.exploit.AutoDDOS;
import il.dev.mio.mod.modules.exploit.NoHitBox;
import il.dev.mio.mod.modules.exploit.LiquidInteract;
import il.dev.mio.mod.modules.exploit.MultiTask;
import il.dev.mio.mod.modules.exploit.TPCoordLogger;
import il.dev.mio.mod.modules.movement.FastWeb;
import il.dev.mio.mod.modules.movement.HoleTP;
import il.dev.mio.mod.modules.misc.GhastNotifier;
import il.dev.mio.mod.modules.misc.ShulkerPreview;
import il.dev.mio.mod.modules.misc.PearlNotify;
import il.dev.mio.mod.modules.misc.Coords;
import il.dev.mio.mod.modules.misc.GhastFarmer;
import il.dev.mio.mod.modules.misc.PopNotify;
import il.dev.mio.mod.modules.misc.ChatModifier;
import il.dev.mio.mod.modules.player.Announcer;
import il.dev.mio.mod.modules.player.FakePlayer;
import il.dev.mio.mod.modules.player.DurabilityMsg;
import il.dev.mio.mod.modules.player.Replenish;
import il.dev.mio.mod.modules.player.FastExp;
import il.dev.mio.mod.modules.combat.HoleFiller;
import il.dev.mio.mod.modules.combat.AutoTrap;
import il.dev.mio.mod.modules.combat.AutoFeetWeb;
import il.dev.mio.mod.modules.combat.Selftrap;
import il.dev.mio.mod.modules.combat.BasePlace;
import il.dev.mio.mod.modules.combat.KeyPearl;
import il.dev.mio.mod.modules.combat.AutoArmor;
import il.dev.mio.mod.modules.combat.ShulkerNuker;
import il.dev.mio.mod.modules.combat.AntiTrap;
import il.dev.mio.mod.modules.combat.Aura;
import il.dev.mio.mod.modules.combat.WebTrap;
import il.dev.mio.mod.modules.render.ESP;
import il.dev.mio.mod.modules.render.LogOutSpots;
import il.dev.mio.mod.modules.render.CustomFog;
import il.dev.mio.mod.modules.render.NameTags;
import il.dev.mio.mod.modules.render.Chams;
import il.dev.mio.mod.modules.render.CrystalChams;
import il.dev.mio.mod.modules.render.Tracers;
import il.dev.mio.mod.modules.render.HandModifier;
import il.dev.mio.mod.modules.render.HoleESP;
import il.dev.mio.mod.modules.render.BlockHighlight;
import il.dev.mio.mod.modules.client.DiscordRPC;
import il.dev.mio.mod.modules.client.HudComponents;
import il.dev.mio.mod.modules.client.FovMod;
import il.dev.mio.mod.modules.client.HUD;
import il.dev.mio.mod.modules.client.FontMod;
import il.dev.mio.mod.modules.client.ClickGui;
import java.util.List;
import il.dev.mio.mod.modules.Module;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import il.dev.mio.mod.ModuleCore;

public class ModuleManager extends ModuleCore
{
    public static Minecraft mc;
    public ArrayList<Module> modules;
    public List<Module> sortedModules;
    public List<String> sortedModulesABC;
    public Animation animationThread;
    public static ArrayList<Module> nigger;
    
    public ModuleManager() {
        this.modules = new ArrayList<Module>();
        this.sortedModules = new ArrayList<Module>();
        this.sortedModulesABC = new ArrayList<String>();
    }
    
    public void init() {
        this.modules.add(new ClickGui());
        this.modules.add(new FontMod());
        this.modules.add(new HUD());
        this.modules.add(new FovMod());
        this.modules.add(new HudComponents());
        this.modules.add(new DiscordRPC());
        this.modules.add(new BlockHighlight());
        this.modules.add(new HoleESP());
        this.modules.add(new HandModifier());
        this.modules.add(new Tracers());
        this.modules.add(new CrystalChams());
        this.modules.add(new Chams());
        this.modules.add(new NameTags());
        this.modules.add(new CustomFog());
        this.modules.add(new LogOutSpots());
        this.modules.add(new ESP());
        this.modules.add(new WebTrap());
        this.modules.add(new Aura());
        this.modules.add(new AntiTrap());
        this.modules.add(new ShulkerNuker());
        this.modules.add(new AutoArmor());
        this.modules.add(new KeyPearl());
        this.modules.add(new BasePlace());
        this.modules.add(new Selftrap());
        this.modules.add(new AutoFeetWeb());
        this.modules.add(new AutoTrap());
        this.modules.add(new HoleFiller());
        this.modules.add(new FastExp());
        this.modules.add(new Replenish());
        this.modules.add(new DurabilityMsg());
        this.modules.add(new FakePlayer());
        this.modules.add(new Announcer());
        this.modules.add(new ChatModifier());
        this.modules.add(new PopNotify());
        this.modules.add(new GhastFarmer());
        this.modules.add(new Coords());
        this.modules.add(new PearlNotify());
        this.modules.add(new ShulkerPreview());
        this.modules.add(new GhastNotifier());
        this.modules.add(new HoleTP());
        this.modules.add(new FastWeb());
        this.modules.add(new TPCoordLogger());
        this.modules.add(new MultiTask());
        this.modules.add(new LiquidInteract());
        this.modules.add(new NoHitBox());
        this.modules.add(new AutoDDOS());
    }
    
    public Module getModuleByName(final String name) {
        for (final Module module : this.modules) {
            if (!module.getName().equalsIgnoreCase(name)) {
                continue;
            }
            return module;
        }
        return null;
    }
    
    public <T extends Module> T getModuleByClass(final Class<T> clazz) {
        for (final Module module : this.modules) {
            if (!clazz.isInstance(module)) {
                continue;
            }
            return (T)module;
        }
        return null;
    }
    
    public void enableModule(final Class<Module> clazz) {
        final Module module = this.getModuleByClass(clazz);
        if (module != null) {
            module.enable();
        }
    }
    
    public void disableModule(final Class<Module> clazz) {
        final Module module = this.getModuleByClass(clazz);
        if (module != null) {
            module.disable();
        }
    }
    
    public void enableModule(final String name) {
        final Module module = this.getModuleByName(name);
        if (module != null) {
            module.enable();
        }
    }
    
    public void disableModule(final String name) {
        final Module module = this.getModuleByName(name);
        if (module != null) {
            module.disable();
        }
    }
    
    public boolean isModuleEnabled(final String name) {
        final Module module = this.getModuleByName(name);
        return module != null && module.isOn();
    }
    
    public boolean isModuleEnabled(final Class<Module> clazz) {
        final Module module = this.getModuleByClass(clazz);
        return module != null && module.isOn();
    }
    
    public Module getModuleByDisplayName(final String displayName) {
        for (final Module module : this.modules) {
            if (!module.getDisplayName().equalsIgnoreCase(displayName)) {
                continue;
            }
            return module;
        }
        return null;
    }
    
    public ArrayList<Module> getEnabledModules() {
        final ArrayList<Module> enabledModules = new ArrayList<Module>();
        for (final Module module : this.modules) {
            if (!module.isEnabled()) {
                continue;
            }
            enabledModules.add(module);
        }
        return enabledModules;
    }
    
    public ArrayList<String> getEnabledModulesName() {
        final ArrayList<String> enabledModules = new ArrayList<String>();
        for (final Module module : this.modules) {
            if (module.isEnabled()) {
                if (!module.isDrawn()) {
                    continue;
                }
                enabledModules.add(module.getFullArrayString());
            }
        }
        return enabledModules;
    }
    
    public ArrayList<Module> getModulesByCategory(Module.Category category) {
        final ArrayList<Module> modulesCategory = new ArrayList<Module>();
        this.modules.forEach(module -> {
            if (module.getCategory() == category) {
                modulesCategory.add(module);
            }
            return;
        });
        return modulesCategory;
    }
    
    public List<Module.Category> getCategories() {
        return Arrays.asList(Module.Category.values());
    }
    
    public void onLoad() {
        this.modules.stream().filter(Module::listening).forEach(MinecraftForge.EVENT_BUS::register);
        this.modules.forEach(Module::onLoad);
    }
    
    public void onUpdate() {
        this.modules.stream().filter(ModuleCore::isEnabled).forEach(Module::onUpdate);
    }
    
    public void onTick() {
        this.modules.stream().filter(ModuleCore::isEnabled).forEach(Module::onTick);
    }
    
    public void onRender2D(final Render2DEvent event) {
        this.modules.stream().filter(ModuleCore::isEnabled).forEach(module -> module.onRender2D(event));
    }
    
    public void onRender3D(final Render3DEvent event) {
        this.modules.stream().filter(ModuleCore::isEnabled).forEach(module -> module.onRender3D(event));
    }

    public void sortModules(boolean reverse) {
        this.sortedModules = this.getEnabledModules().stream().filter(Module::isDrawn).sorted(Comparator.comparing(module -> this.renderer.getStringWidth(module.getFullArrayString()) * (reverse ? -1 : 1))).collect(Collectors.toList());
    }
    
    public void sortModulesABC() {
        (this.sortedModulesABC = new ArrayList<String>(this.getEnabledModulesName())).sort(String.CASE_INSENSITIVE_ORDER);
    }
    
    public void onLogout() {
        this.modules.forEach(Module::onLogout);
    }
    
    public void onLogin() {
        this.modules.forEach(Module::onLogin);
    }
    
    public void onUnload() {
        this.modules.forEach(MinecraftForge.EVENT_BUS::unregister);
        this.modules.forEach(Module::onUnload);
    }
    
    public void onUnloadPost() {
        for (final Module module : this.modules) {
            module.enabled.setValue(false);
        }
    }
    
    public void onKeyPressed(final int eventKey) {
        if (eventKey == 0 || !Keyboard.getEventKeyState() || ModuleManager.mc.currentScreen instanceof MioClickGui) {
            return;
        }
        this.modules.forEach(module -> {
            if (module.getBind().getKey() == eventKey) {
                module.toggle();
            }
        });
    }
    
    public static ArrayList<Module> getModules() {
        return ModuleManager.nigger;
    }
    
    public static boolean isModuleEnablednigger(final String name) {
        final Module modulenigger = getModules().stream().filter(mm -> mm.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
        return modulenigger.isEnabled();
    }
    
    public static boolean isModuleEnablednigger(final Module modulenigger) {
        return modulenigger.isEnabled();
    }
    
    static {
        ModuleManager.mc = Minecraft.getMinecraft();
    }
    
    private class Animation extends Thread
    {
        public Module module;
        public float offset;
        public float vOffset;
        ScheduledExecutorService service;
        
        public Animation() {
            super("Animation");
            this.service = Executors.newSingleThreadScheduledExecutor();
        }
        
        @Override
        public void run() {
            if (HUD.getInstance().renderingMode.getValue() == HUD.RenderingMode.Length) {
                for (final Module module : ModuleManager.this.sortedModules) {
                    final String text = module.getDisplayName() + ChatFormatting.GRAY + ((module.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + module.getDisplayInfo() + ChatFormatting.GRAY + "]") : "");
                    module.offset = ModuleManager.this.renderer.getStringWidth(text) / (float)HUD.getInstance().animationHorizontalTime.getValue();
                    module.vOffset = ModuleManager.this.renderer.getFontHeight() / (float)HUD.getInstance().animationVerticalTime.getValue();
                    if (module.isEnabled() && HUD.getInstance().animationHorizontalTime.getValue() != 1) {
                        if (module.arrayListOffset <= module.offset) {
                            continue;
                        }
                        if (IMinecraftUtil.mc.world == null) {
                            continue;
                        }
                        final Module module3 = module;
                        module3.arrayListOffset -= module.offset;
                        module.sliding = true;
                    }
                    else {
                        if (!module.isDisabled()) {
                            continue;
                        }
                        if (HUD.getInstance().animationHorizontalTime.getValue() == 1) {
                            continue;
                        }
                        if (module.arrayListOffset < ModuleManager.this.renderer.getStringWidth(text) && IMinecraftUtil.mc.world != null) {
                            final Module module4 = module;
                            module4.arrayListOffset += module.offset;
                            module.sliding = true;
                        }
                        else {
                            module.sliding = false;
                        }
                    }
                }
            }
            else {
                for (final String e : ModuleManager.this.sortedModulesABC) {
                    final Module module2 = Mio.moduleManager.getModuleByName(e);
                    final String text2 = module2.getDisplayName() + ChatFormatting.GRAY + ((module2.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + module2.getDisplayInfo() + ChatFormatting.GRAY + "]") : "");
                    module2.offset = ModuleManager.this.renderer.getStringWidth(text2) / (float)HUD.getInstance().animationHorizontalTime.getValue();
                    module2.vOffset = ModuleManager.this.renderer.getFontHeight() / (float)HUD.getInstance().animationVerticalTime.getValue();
                    if (module2.isEnabled() && HUD.getInstance().animationHorizontalTime.getValue() != 1) {
                        if (module2.arrayListOffset <= module2.offset) {
                            continue;
                        }
                        if (IMinecraftUtil.mc.world == null) {
                            continue;
                        }
                        final Module module5 = module2;
                        module5.arrayListOffset -= module2.offset;
                        module2.sliding = true;
                    }
                    else {
                        if (!module2.isDisabled()) {
                            continue;
                        }
                        if (HUD.getInstance().animationHorizontalTime.getValue() == 1) {
                            continue;
                        }
                        if (module2.arrayListOffset < ModuleManager.this.renderer.getStringWidth(text2) && IMinecraftUtil.mc.world != null) {
                            final Module module6 = module2;
                            module6.arrayListOffset += module2.offset;
                            module2.sliding = true;
                        }
                        else {
                            module2.sliding = false;
                        }
                    }
                }
            }
        }
        
        @Override
        public void start() {
            System.out.println("Starting animation thread.");
            this.service.scheduleAtFixedRate(this, 0L, 1L, TimeUnit.MILLISECONDS);
        }
    }
}
