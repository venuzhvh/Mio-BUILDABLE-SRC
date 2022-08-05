//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.mod;

import java.util.Iterator;
import java.util.Collection;
import il.dev.mio.mod.gui.MioClickGui;
import il.dev.mio.mod.modules.Module;
import il.dev.mio.Mio;
import java.util.ArrayList;
import il.dev.mio.api.manager.TextManager;
import il.dev.mio.mod.gui.clickgui.setting.Setting;
import java.util.List;
import il.dev.mio.api.util.IMinecraftUtil;

public class ModuleCore implements IMinecraftUtil
{
    public List<Setting> settings;
    public TextManager renderer;
    private String name;
    
    public ModuleCore() {
        this.settings = new ArrayList<Setting>();
        this.renderer = Mio.textManager;
    }
    
    public ModuleCore(final String name) {
        this.settings = new ArrayList<Setting>();
        this.renderer = Mio.textManager;
        this.name = name;
    }
    
    public static boolean nullCheck() {
        return ModuleCore.mc.player == null;
    }
    
    public static boolean fullNullCheck() {
        return ModuleCore.mc.player == null || ModuleCore.mc.world == null;
    }
    
    public String getName() {
        return this.name;
    }
    
    public List<Setting> getSettings() {
        return this.settings;
    }
    
    public boolean hasSettings() {
        return !this.settings.isEmpty();
    }
    
    public boolean isEnabled() {
        return this instanceof Module && ((Module)this).isOn();
    }
    
    public boolean isDisabled() {
        return !this.isEnabled();
    }
    
    public Setting register(final Setting setting) {
        setting.setFeature(this);
        this.settings.add(setting);
        if (this instanceof Module && ModuleCore.mc.currentScreen instanceof MioClickGui) {
            MioClickGui.getInstance().updateModule((Module)this);
        }
        return setting;
    }
    
    public void unregister(final Setting settingIn) {
        final ArrayList<Setting> removeList = new ArrayList<Setting>();
        for (final Setting setting : this.settings) {
            if (!setting.equals(settingIn)) {
                continue;
            }
            removeList.add(setting);
        }
        if (!removeList.isEmpty()) {
            this.settings.removeAll(removeList);
        }
        if (this instanceof Module && ModuleCore.mc.currentScreen instanceof MioClickGui) {
            MioClickGui.getInstance().updateModule((Module)this);
        }
    }
    
    public Setting getSettingByName(final String name) {
        for (final Setting setting : this.settings) {
            if (!setting.getName().equalsIgnoreCase(name)) {
                continue;
            }
            return setting;
        }
        return null;
    }
    
    public void reset() {
        for (final Setting setting : this.settings) {
            setting.setValue(setting.getDefaultValue());
        }
    }
    
    public void clearSettings() {
        this.settings = new ArrayList<Setting>();
    }
}
