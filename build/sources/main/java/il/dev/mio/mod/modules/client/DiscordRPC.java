// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.mod.modules.client;

import il.dev.mio.api.util.RichPresenceUtil;
import il.dev.mio.mod.gui.clickgui.setting.Setting;
import il.dev.mio.mod.modules.Module;

public class DiscordRPC extends Module
{
    public static DiscordRPC INSTANCE;
    public Setting<String> rpcState;
    
    public DiscordRPC() {
        super("DiscordRPC", "Discord rich presence", Category.CLIENT, false, false, false);
        this.rpcState = (Setting<String>)this.register(new Setting("State", "Gaming"));
        DiscordRPC.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        RichPresenceUtil.start();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        RichPresenceUtil.stop();
    }
    
    @Override
    public void onLoad() {
        super.onLoad();
        if (this.isOn()) {
            RichPresenceUtil.start();
        }
    }
}
