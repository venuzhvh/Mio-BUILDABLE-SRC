//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.api.event.events;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.math.Vec3d;
import il.dev.mio.api.event.EventStage;

public class RenderEvent extends EventStage
{
    private Vec3d renderPos;
    private Tessellator tessellator;
    private final float partialTicks;
    
    public void resetTranslation() {
        this.setTranslation(this.renderPos);
    }
    
    public Vec3d getRenderPos() {
        return this.renderPos;
    }
    
    public BufferBuilder getBuffer() {
        return this.tessellator.getBuffer();
    }
    
    public Tessellator getTessellator() {
        return this.tessellator;
    }
    
    public RenderEvent(final Tessellator paramTessellator, final Vec3d paramVec3d, final float ticks) {
        this.tessellator = paramTessellator;
        this.renderPos = paramVec3d;
        this.partialTicks = ticks;
    }
    
    public void setTranslation(final Vec3d paramVec3d) {
        this.getBuffer().setTranslation(-paramVec3d.x, -paramVec3d.y, -paramVec3d.z);
    }
    
    public float getPartialTicks() {
        return this.partialTicks;
    }
}
