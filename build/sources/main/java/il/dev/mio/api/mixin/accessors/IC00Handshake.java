// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.api.mixin.accessors;

import org.spongepowered.asm.mixin.gen.Accessor;
import net.minecraft.network.handshake.client.C00Handshake;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ C00Handshake.class })
public interface IC00Handshake
{
    @Accessor("ip")
    String getIp();
    
    @Accessor("ip")
    void setIp(final String p0);
}
