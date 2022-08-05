// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.api.event.events;

import il.dev.mio.mod.gui.clickgui.setting.Setting;
import il.dev.mio.mod.ModuleCore;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import il.dev.mio.api.event.EventStage;

@Cancelable
public class ClientEvent extends EventStage
{
    private ModuleCore feature;
    private Setting setting;
    
    public ClientEvent(final int stage, final ModuleCore moduleCore) {
        super(stage);
        this.feature = moduleCore;
    }
    
    public ClientEvent(final Setting setting) {
        super(2);
        this.setting = setting;
    }
    
    public ModuleCore getFeature() {
        return this.feature;
    }
    
    public Setting getSetting() {
        return this.setting;
    }
}
