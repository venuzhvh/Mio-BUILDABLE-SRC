// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.api.event.events;

import net.minecraft.entity.player.EntityPlayer;
import il.dev.mio.api.event.EventStage;

public class TotemPopEvent extends EventStage
{
    private final EntityPlayer entity;
    
    public TotemPopEvent(final EntityPlayer entity) {
        this.entity = entity;
    }
    
    public EntityPlayer getEntity() {
        return this.entity;
    }
}
