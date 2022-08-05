// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.api.event.events;

import net.minecraft.util.math.BlockPos;
import il.dev.mio.api.event.EventStage;

public class BlockDestructionEvent extends EventStage
{
    BlockPos nigger;
    
    public BlockDestructionEvent(BlockPos nigger) {
        nigger = nigger;
    }
    
    public BlockPos getBlockPos() {
        return this.nigger;
    }
}
