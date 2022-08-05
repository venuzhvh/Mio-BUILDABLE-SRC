// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.api.event.events;

import net.minecraftforge.fml.common.eventhandler.Event;

public class MotionUpdateEvent extends Event
{
    public float rotationYaw;
    public float rotationPitch;
    public double posX;
    public double posY;
    public double posZ;
    public boolean onGround;
    public boolean noClip;
    public int stage;
    
    public MotionUpdateEvent(final float rotationYaw, final float rotationPitch, final double posX, final double posY, final double posZ, final boolean onGround, final boolean noClip, final int stage) {
        this.rotationYaw = rotationYaw;
        this.rotationPitch = rotationPitch;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.onGround = onGround;
        this.noClip = noClip;
        this.stage = stage;
    }
}
