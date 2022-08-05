// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.api.mixin.accessors;

import org.spongepowered.asm.mixin.gen.Invoker;
import net.minecraft.client.multiplayer.ServerAddress;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ServerAddress.class })
public interface IServerAddress
{
    @Invoker("getServerAddress")
    default String[] getServerAddress(final String string) {
        throw new IllegalStateException("Mixin didnt transform this");
    }
}
