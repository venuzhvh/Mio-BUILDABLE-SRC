// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.api.event.events;

import il.dev.mio.api.event.EventStage;

public class PerspectiveEvent extends EventStage
{
    private float aspect;
    
    public PerspectiveEvent(final float aspect) {
        this.aspect = aspect;
    }
    
    public float getAspect() {
        return this.aspect;
    }
    
    public void setAspect(final float aspect) {
        this.aspect = aspect;
    }
}
