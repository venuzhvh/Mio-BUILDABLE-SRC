// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.api.event.events;

import net.minecraftforge.fml.common.eventhandler.Cancelable;
import il.dev.mio.api.event.EventStage;

@Cancelable
public class ChatEvent extends EventStage
{
    private final String msg;
    
    public ChatEvent(final String msg) {
        this.msg = msg;
    }
    
    public String getMsg() {
        return this.msg;
    }
}
